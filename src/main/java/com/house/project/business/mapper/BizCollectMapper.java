package com.house.project.business.mapper;

import java.math.BigDecimal;
import java.util.List;
import com.house.project.business.domain.BizCollect;
import com.house.project.business.domain.BizReport;

/**
 * 统计管理Mapper接口
 * 
 * @author bo.zhang
 * @date 2020-08-12
 */
public interface BizCollectMapper 
{

    /**
     * 当月到访数
     * @param bizReport 参数
     * @return 结果
     */
    public int selectMonthVisitNum(BizReport bizReport);

    /**
     * 当月成交数
     * @param report 参数
     * @return 结果
     */
    public int selectMonthDealNum(BizReport report);

    /**
     * 当月成交金额
     * @param report 参数
*      @return 结果
     */
    public BigDecimal selectMonthDealAmt(BizReport report);

    /**
     * 分别统计1-12月份的报备数量
     * @param report 参数
     * @return 结果
     */
    public List<Integer> selectAllYearReportCount(BizReport report);

    /**
     * 分别统计1-12月份的到访数
     * @param report 参数
     * @return 结果
     */
    public List<Integer> selectAllYearVisitNum(BizReport report);

    /**
     * 分别统计1-12月份的成交数
     * @param report 参数
     * @return 结果
     */
    public List<Integer> selectAllYearDealNum(BizReport report);

    /**
     * 分别统计1-12月份的成交金额
     * @param report 参数
     * @return 结果
     */
    public List<BigDecimal> selectAllYearDealAmt(BizReport report);

    /**
     * 所有报备数量
     * @param bizReport 参数
     * @return 结果
     */
    public int selectAllReportCount(BizReport bizReport);

    /**
     * 查询统计管理
     * 
     * @param id 统计管理ID
     * @return 统计管理
     */
    public BizCollect selectBizCollectById(Integer id);

    /**
     * 查询统计管理列表
     * 
     * @param bizCollect 统计管理
     * @return 统计管理集合
     */
    public List<BizCollect> selectBizCollectList(BizCollect bizCollect);

    /**
     * 新增统计管理
     * 
     * @param bizCollect 统计管理
     * @return 结果
     */
    public int insertBizCollect(BizCollect bizCollect);

    /**
     * 修改统计管理
     * 
     * @param bizCollect 统计管理
     * @return 结果
     */
    public int updateBizCollect(BizCollect bizCollect);

    /**
     * 删除统计管理
     * 
     * @param id 统计管理ID
     * @return 结果
     */
    public int deleteBizCollectById(Integer id);

    /**
     * 批量删除统计管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizCollectByIds(Integer[] ids);
}
