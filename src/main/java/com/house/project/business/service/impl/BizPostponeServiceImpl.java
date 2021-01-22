package com.house.project.business.service.impl;

import java.util.List;

import com.house.common.constant.Constants;
import com.house.common.enums.BizPostponeStatus;
import com.house.common.utils.DateUtils;
import com.house.common.utils.SecurityUtils;
import com.house.project.business.domain.BizReport;
import com.house.project.business.mapper.BizReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.project.business.mapper.BizPostponeMapper;
import com.house.project.business.domain.BizPostpone;
import com.house.project.business.service.IBizPostponeService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 延期申请Service业务层处理
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
@Service
public class BizPostponeServiceImpl implements IBizPostponeService 
{
    @Autowired
    private BizPostponeMapper bizPostponeMapper;
    @Autowired
    private BizReportMapper reportMapper;

    /**
     * 查询待审核的延期申请
     * @param reportNo 报备编号
     * @return 结果
     */
    @Override
    public BizPostpone selectNotApproveData(String reportNo) {
        return bizPostponeMapper.selectNotApproveData(reportNo);
    }

    /**
     * 审核信息延期新增
     * @param report 报备
     * @param postpone 延期申请
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int approve(BizReport report, BizPostpone postpone) {
        reportMapper.updateBizReport(report);
        return bizPostponeMapper.updateBizPostpone(postpone);
    }

    /**
     *
     * @param reportId 报备id
     * @param postpone 延期申请信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addPostpone(Integer reportId, BizPostpone postpone) {
        BizReport report = new BizReport();
        report.setId(reportId);
        report.setPostponeStatus(BizPostponeStatus.BE_REVIEWED.getCode());
        report.setUpdateBy(SecurityUtils.getUsername());
        reportMapper.updateBizReport(report);
        return bizPostponeMapper.insertBizPostpone(postpone);
    }

    /**
     * 查询延期申请
     * 
     * @param id 延期申请ID
     * @return 延期申请
     */
    @Override
    public BizPostpone selectBizPostponeById(Integer id)
    {
        return bizPostponeMapper.selectBizPostponeById(id);
    }

    /**
     * 查询延期申请列表
     * 
     * @param bizPostpone 延期申请
     * @return 延期申请
     */
    @Override
    public List<BizPostpone> selectBizPostponeList(BizPostpone bizPostpone)
    {
        return bizPostponeMapper.selectBizPostponeList(bizPostpone);
    }

    /**
     * 新增延期申请
     * 
     * @param bizPostpone 延期申请
     * @return 结果
     */
    @Override
    public int insertBizPostpone(BizPostpone bizPostpone)
    {
        bizPostpone.setCreateTime(DateUtils.getNowDate());
        return bizPostponeMapper.insertBizPostpone(bizPostpone);
    }

    /**
     * 修改延期申请
     * 
     * @param bizPostpone 延期申请
     * @return 结果
     */
    @Override
    public int updateBizPostpone(BizPostpone bizPostpone)
    {
        bizPostpone.setUpdateTime(DateUtils.getNowDate());
        return bizPostponeMapper.updateBizPostpone(bizPostpone);
    }

    /**
     * 批量删除延期申请
     * 
     * @param ids 需要删除的延期申请ID
     * @return 结果
     */
    @Override
    public int deleteBizPostponeByIds(Integer[] ids)
    {
        return bizPostponeMapper.deleteBizPostponeByIds(ids);
    }

    /**
     * 删除延期申请信息
     * 
     * @param id 延期申请ID
     * @return 结果
     */
    @Override
    public int deleteBizPostponeById(Integer id)
    {
        return bizPostponeMapper.deleteBizPostponeById(id);
    }
}
