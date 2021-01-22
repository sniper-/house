package com.house.project.business.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.house.framework.aspectj.lang.annotation.Excel;
import com.house.framework.web.domain.BaseEntity;

/**
 * 统计管理对象 biz_collect
 * 
 */
public class BizCollect extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增 */
    private Integer id;

    /** 楼盘编号 */
    @Excel(name = "楼盘编号")
    private String projectId;

    /** 日期 YYYY-MM-DD */
    @Excel(name = "日期 YYYY-MM-DD")
    private String date;

    /** 报备数 */
    @Excel(name = "报备数")
    private Integer reportNum;

    /** 到访数 */
    @Excel(name = "到访数")
    private Integer visitNum;

    /** 成交数 */
    @Excel(name = "成交数")
    private Integer settledNum;

    /** 未成交数 */
    @Excel(name = "未成交数")
    private Integer unsettledNum;

    /** 月成交数 */
    @Excel(name = "月成交数")
    private Integer monthSettledNum;

    /** 月成交金额 */
    @Excel(name = "月成交金额")
    private BigDecimal monthSettledAmount;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setProjectId(String projectId) 
    {
        this.projectId = projectId;
    }

    public String getProjectId() 
    {
        return projectId;
    }
    public void setDate(String date) 
    {
        this.date = date;
    }

    public String getDate() 
    {
        return date;
    }
    public void setReportNum(Integer reportNum) 
    {
        this.reportNum = reportNum;
    }

    public Integer getReportNum() 
    {
        return reportNum;
    }
    public void setVisitNum(Integer visitNum) 
    {
        this.visitNum = visitNum;
    }

    public Integer getVisitNum() 
    {
        return visitNum;
    }
    public void setSettledNum(Integer settledNum) 
    {
        this.settledNum = settledNum;
    }

    public Integer getSettledNum() 
    {
        return settledNum;
    }
    public void setUnsettledNum(Integer unsettledNum) 
    {
        this.unsettledNum = unsettledNum;
    }

    public Integer getUnsettledNum() 
    {
        return unsettledNum;
    }
    public void setMonthSettledNum(Integer monthSettledNum) 
    {
        this.monthSettledNum = monthSettledNum;
    }

    public Integer getMonthSettledNum() 
    {
        return monthSettledNum;
    }
    public void setMonthSettledAmount(BigDecimal monthSettledAmount) 
    {
        this.monthSettledAmount = monthSettledAmount;
    }

    public BigDecimal getMonthSettledAmount() 
    {
        return monthSettledAmount;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("date", getDate())
            .append("reportNum", getReportNum())
            .append("visitNum", getVisitNum())
            .append("settledNum", getSettledNum())
            .append("unsettledNum", getUnsettledNum())
            .append("monthSettledNum", getMonthSettledNum())
            .append("monthSettledAmount", getMonthSettledAmount())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
