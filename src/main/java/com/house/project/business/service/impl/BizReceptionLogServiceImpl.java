package com.house.project.business.service.impl;

import java.util.List;
import com.house.common.utils.DateUtils;
import com.house.common.utils.SecurityUtils;
import com.house.common.utils.StringUtils;
import com.house.project.business.mapper.BizReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.project.business.mapper.BizReceptionLogMapper;
import com.house.project.business.domain.BizReceptionLog;
import com.house.project.business.service.IBizReceptionLogService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 接待日志Service业务层处理
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
@Service
public class BizReceptionLogServiceImpl implements IBizReceptionLogService 
{
    @Autowired
    private BizReceptionLogMapper bizReceptionLogMapper;
    @Autowired
    private BizReportMapper reportMapper;

    /**
     * 查询接待日志
     * 
     * @param id 接待日志ID
     * @return 接待日志
     */
    @Override
    public BizReceptionLog selectBizReceptionLogById(Integer id)
    {
        return bizReceptionLogMapper.selectBizReceptionLogById(id);
    }

    /**
     * 查询接待日志列表
     * 
     * @param bizReceptionLog 接待日志
     * @return 接待日志
     */
    @Override
    public List<BizReceptionLog> selectBizReceptionLogList(BizReceptionLog bizReceptionLog)
    {
        return bizReceptionLogMapper.selectBizReceptionLogList(bizReceptionLog);
    }

    /**
     * 新增接待日志
     * 
     * @param bizReceptionLog 接待日志
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertBizReceptionLog(BizReceptionLog bizReceptionLog)
    {
        bizReceptionLog.setCreateTime(DateUtils.getNowDate());
        bizReceptionLog.setCreateBy(SecurityUtils.getUsername());
        // 报备到访次数加1
        if (!StringUtils.isEmpty(bizReceptionLog.getReportNo())) {
            reportMapper.updateAddVisitTime(bizReceptionLog.getReportNo());
        }
        return bizReceptionLogMapper.insertBizReceptionLog(bizReceptionLog);
    }

    /**
     * 修改接待日志
     * 
     * @param bizReceptionLog 接待日志
     * @return 结果
     */
    @Override
    public int updateBizReceptionLog(BizReceptionLog bizReceptionLog)
    {
        bizReceptionLog.setUpdateTime(DateUtils.getNowDate());
        return bizReceptionLogMapper.updateBizReceptionLog(bizReceptionLog);
    }

    /**
     * 批量删除接待日志
     * 
     * @param ids 需要删除的接待日志ID
     * @return 结果
     */
    @Override
    public int deleteBizReceptionLogByIds(Integer[] ids)
    {
        return bizReceptionLogMapper.deleteBizReceptionLogByIds(ids);
    }

    /**
     * 删除接待日志信息
     * 
     * @param id 接待日志ID
     * @return 结果
     */
    @Override
    public int deleteBizReceptionLogById(Integer id)
    {
        return bizReceptionLogMapper.deleteBizReceptionLogById(id);
    }
}
