package com.house.project.business.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.house.common.utils.DateUtils;
import com.house.project.business.domain.BizReport;
import com.house.project.business.domain.vo.BizCollectVo;
import com.house.project.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.project.business.mapper.BizCollectMapper;
import com.house.project.business.domain.BizCollect;
import com.house.project.business.service.IBizCollectService;

/**
 * 统计管理Service业务层处理
 * 
 * @author bo.zhang
 * @date 2020-08-12
 */
@Service
public class BizCollectServiceImpl implements IBizCollectService 
{
    @Autowired
    private BizCollectMapper bizCollectMapper;
    @Autowired
    private SysDeptMapper deptMapper;

    @Override
    public BizCollectVo queryCollect(Integer purchasedProjectId, Long userId) {
        // 查询服务商编号
        Long serviceProviderId = deptMapper.selectServiceProviderId(userId);
        BizCollectVo vo = new BizCollectVo();
        BizReport report = new BizReport();
        report.setServiceProviderId(serviceProviderId); // 服务商编号
        report.setPurchasedProjectId(purchasedProjectId); // 成交楼盘编号  报备和到访是意向编号
        report.setTempValue(DateUtils.dateTimeNow(DateUtils.YYYY)); // 当前年份
        // 报备数量
        int reportNum = bizCollectMapper.selectAllReportCount(report);
        // 当月到访数
        int monthVisitNum = bizCollectMapper.selectMonthVisitNum(report);
        // 当月成交数
        int monthDealNum = bizCollectMapper.selectMonthDealNum(report);
        // 当月成交金额
        BigDecimal monthSettledAmount = bizCollectMapper.selectMonthDealAmt(report);

        vo.setReportNum(reportNum);
        vo.setVisitNum(monthVisitNum);
        vo.setDealNum(monthDealNum);
        vo.setMonthSettledAmount(monthSettledAmount);

        // 1-12月份的报备数
        List<Integer> reportNumList = bizCollectMapper.selectAllYearReportCount(report);
        // 1-12月份的到访数
        List<Integer> visitNumList = bizCollectMapper.selectAllYearVisitNum(report);
        // 1-12月份的成交数
        List<Integer> dealNumList = bizCollectMapper.selectAllYearDealNum(report);
        // 1-12月份的成交金额集合
        List<BigDecimal> monthSettledList  = bizCollectMapper.selectAllYearDealAmt(report);

        vo.setReportNumList(reportNumList);
        vo.setVisitNumList(visitNumList);
        vo.setDealNumList(dealNumList);
        vo.setMonthSettledList(monthSettledList);
        return vo;
    }

    /**
     * 查询统计管理
     * 
     * @param id 统计管理ID
     * @return 统计管理
     */
    @Override
    public BizCollect selectBizCollectById(Integer id)
    {
        return bizCollectMapper.selectBizCollectById(id);
    }

    /**
     * 查询统计管理列表
     * 
     * @param bizCollect 统计管理
     * @return 统计管理
     */
    @Override
    public List<BizCollect> selectBizCollectList(BizCollect bizCollect)
    {
        return bizCollectMapper.selectBizCollectList(bizCollect);
    }

    /**
     * 新增统计管理
     * 
     * @param bizCollect 统计管理
     * @return 结果
     */
    @Override
    public int insertBizCollect(BizCollect bizCollect)
    {
        bizCollect.setCreateTime(DateUtils.getNowDate());
        return bizCollectMapper.insertBizCollect(bizCollect);
    }

    /**
     * 修改统计管理
     * 
     * @param bizCollect 统计管理
     * @return 结果
     */
    @Override
    public int updateBizCollect(BizCollect bizCollect)
    {
        bizCollect.setUpdateTime(DateUtils.getNowDate());
        return bizCollectMapper.updateBizCollect(bizCollect);
    }

    /**
     * 批量删除统计管理
     * 
     * @param ids 需要删除的统计管理ID
     * @return 结果
     */
    @Override
    public int deleteBizCollectByIds(Integer[] ids)
    {
        return bizCollectMapper.deleteBizCollectByIds(ids);
    }

    /**
     * 删除统计管理信息
     * 
     * @param id 统计管理ID
     * @return 结果
     */
    @Override
    public int deleteBizCollectById(Integer id)
    {
        return bizCollectMapper.deleteBizCollectById(id);
    }
}
