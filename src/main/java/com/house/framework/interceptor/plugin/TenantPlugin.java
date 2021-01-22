package com.house.framework.interceptor.plugin;

import com.google.common.collect.Lists;
import com.house.common.enums.UserStatus;
import com.house.common.utils.StringUtils;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;

@Component
@Intercepts({
        @Signature(
                type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}
        )
})
public class TenantPlugin implements Interceptor {

    protected transient Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String STATUS_COLUMN = "status";

    private static List<String> IGNORE_TENANT_TABLES = Lists.newArrayList("com.house.project.monitor.mapper",
            "com.house.project.system.mapper",
            "com.house.project.business.mapper.BizSysTenantMapper",
            "com.house.project.tool.gen.mapper",
            "com.house.project.business.mapper.BizCollectMapper.selectAllYearDealAmt",
            "com.house.project.business.mapper.BizCollectMapper.selectAllYearVisitNum",
            "com.house.project.business.mapper.BizCollectMapper.selectAllYearDealNum",
            "com.house.project.business.mapper.BizCollectMapper.selectAllYearReportCount");

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        // 先拦截到RoutingStatementHandler，里面有个StatementHandler类型的delegate变量，
        // 实现类是BaseStatementHandler，然后就到BaseStatementHandler的成员变量mappedStatement
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        //id为执行的mapper方法的全路径名
        String id = mappedStatement.getId();

        if (StringUtils.isEmpty(id)) {
            return invocation.proceed();
        }

        for (String name : IGNORE_TENANT_TABLES) {
            if (id.contains(name)) {
                return invocation.proceed();
            }
        }
        boolean hasStatus = false;
        if (id.contains("com.house.project.business")) {
            hasStatus = true;
        }
        //sql语句类型 select、delete、insert、update
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        switch (sqlCommandType) {
            case SELECT:
                fillSelectField(statementHandler, metaObject, hasStatus);
                break;
            case INSERT:
                fillInsertField(statementHandler, metaObject);
                break;
            case UPDATE:
                fillUpdateField(statementHandler, metaObject);
                break;
            default:
                break;
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

    public static void main(String[] args) {

    }

    /**
     * 查询操作
     * @param statementHandler StatementHandler
     * @param metaObject MetaObject
     * @throws Exception 异常
     */
    private void fillSelectField(StatementHandler statementHandler, MetaObject metaObject, boolean hasStatus) throws Exception {
        BoundSql boundSql = statementHandler.getBoundSql();
        //获取到原始sql语句
        String sql = boundSql.getSql();
        StringBuilder whereSql = new StringBuilder();
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Select select = (Select) parserManager.parse(new StringReader(sql));
        PlainSelect plain = (PlainSelect) select.getSelectBody();
        Table table = (Table) plain.getFromItem();
        Alias alias = table.getAlias();
        //增加sql语句的逻辑部分处理
        String aliaName = "";
        if (alias != null && !StringUtils.isEmpty(alias.getName())) {
            aliaName = alias.getName().concat(".");
        }
        if (hasStatus) {
            whereSql.append(aliaName).append(STATUS_COLUMN).append(" = ").append(UserStatus.OK.getCode());
        }
        // 获取当前查询条件
        Expression where = plain.getWhere();
        if (where == null) {
            if (whereSql.length() > 0) {
                Expression expression = CCJSqlParserUtil
                        .parseCondExpression(whereSql.toString());
                plain.setWhere(expression);
            }
        } else {
            if (whereSql.length() > 0) {
                //where条件之前存在，需要重新进行拼接
                whereSql.append(" and ( ").append(where.toString()).append(" )");
            } else {
                //新增片段不存在，使用之前的sql
                whereSql.append(where.toString());
            }
            Expression expression = CCJSqlParserUtil.parseCondExpression(whereSql.toString());
            plain.setWhere(expression);
        }
        metaObject.setValue("delegate.boundSql.sql", select.toString());
    }

    /**
     * 新增操作
     * @param statementHandler StatementHandler
     * @param metaObject MetaObject
     * @throws Exception 异常
     */
    private void fillInsertField(StatementHandler statementHandler, MetaObject metaObject) throws Exception {
        BoundSql boundSql = statementHandler.getBoundSql();
        //获取到原始sql语句
        String sql = boundSql.getSql();
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Insert insert = (Insert) parserManager.parse(new StringReader(sql));
        ExpressionList expressionList = (ExpressionList) insert.getItemsList();
        List<Expression> expressions = expressionList.getExpressions();
        List<Column> columns = insert.getColumns();
        // 判断字段是否已存在
        ColumnExist exist = checkCloumn(columns);
        // 状态赋值
        if (!exist.isStatus()) {
            columns.add(new Column(STATUS_COLUMN));
            expressions.add(new LongValue(UserStatus.OK.getCode()));
        }
        insert.setColumns(columns);
        expressionList.setExpressions(expressions);
        insert.setItemsList(expressionList);
        metaObject.setValue("delegate.boundSql.sql", insert.toString());
    }

    /**
     * 更新操作
     * @param statementHandler StatementHandler
     * @param metaObject MetaObject
     * @throws Exception 异常
     */
    private void fillUpdateField(StatementHandler statementHandler, MetaObject metaObject) throws Exception {
        BoundSql boundSql = statementHandler.getBoundSql();
        //获取到原始sql语句
        String sql = boundSql.getSql();
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Update update = (Update) parserManager.parse(new StringReader(sql));
        List<Expression> expressions = update.getExpressions();
        List<Column> columns = update.getColumns();

        // 判断字段是否已存在
        ColumnExist exist = checkCloumn(columns);
        update.setColumns(columns);
        update.setExpressions(expressions);
        metaObject.setValue("delegate.boundSql.sql", update.toString());
    }

    /**
     * 判断字段是否已在sql中存在
     * @param columns 字段列表
     * @return
     */
    private ColumnExist checkCloumn(List<Column> columns) {
        ColumnExist exist = new ColumnExist();
        for (Column c : columns) {
            if (c.getColumnName().equals(STATUS_COLUMN)) {
                exist.setStatus(true);
            }
        }
        return exist;
    }

    class ColumnExist {
        private boolean isStatus;

        public ColumnExist() {
            isStatus = false;
        }

        public boolean isStatus() {
            return isStatus;
        }

        public void setStatus(boolean status) {
            isStatus = status;
        }
    }

}
