package com.house.project.business.service.impl;

import java.util.List;
import com.house.common.utils.DateUtils;
import com.house.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.project.business.mapper.BizDimissionDistributionMapper;
import com.house.project.business.domain.BizDimissionDistribution;
import com.house.project.business.service.IBizDimissionDistributionService;

/**
 * 离职分配Service业务层处理
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
@Service
public class BizDimissionDistributionServiceImpl implements IBizDimissionDistributionService 
{
    @Autowired
    private BizDimissionDistributionMapper bizDimissionDistributionMapper;

    /**
     * 查询离职分配
     * 
     * @param id 离职分配ID
     * @return 离职分配
     */
    @Override
    public BizDimissionDistribution selectBizDimissionDistributionById(Integer id)
    {
        return bizDimissionDistributionMapper.selectBizDimissionDistributionById(id);
    }

    /**
     * 查询离职分配列表
     * 
     * @param bizDimissionDistribution 离职分配
     * @return 离职分配
     */
    @Override
    public List<BizDimissionDistribution> selectBizDimissionDistributionList(BizDimissionDistribution bizDimissionDistribution)
    {
        return bizDimissionDistributionMapper.selectBizDimissionDistributionList(bizDimissionDistribution);
    }

    /**
     * 新增离职分配
     * 
     * @param bizDimissionDistribution 离职分配
     * @return 结果
     */
    @Override
    public int insertBizDimissionDistribution(BizDimissionDistribution bizDimissionDistribution)
    {
        bizDimissionDistribution.setCreateTime(DateUtils.getNowDate());
        bizDimissionDistribution.setCreateBy(SecurityUtils.getUsername());
        return bizDimissionDistributionMapper.insertBizDimissionDistribution(bizDimissionDistribution);
    }

    /**
     * 修改离职分配
     * 
     * @param bizDimissionDistribution 离职分配
     * @return 结果
     */
    @Override
    public int updateBizDimissionDistribution(BizDimissionDistribution bizDimissionDistribution)
    {
        bizDimissionDistribution.setUpdateTime(DateUtils.getNowDate());
        return bizDimissionDistributionMapper.updateBizDimissionDistribution(bizDimissionDistribution);
    }

    /**
     * 批量删除离职分配
     * 
     * @param ids 需要删除的离职分配ID
     * @return 结果
     */
    @Override
    public int deleteBizDimissionDistributionByIds(Integer[] ids)
    {
        return bizDimissionDistributionMapper.deleteBizDimissionDistributionByIds(ids);
    }

    /**
     * 删除离职分配信息
     * 
     * @param id 离职分配ID
     * @return 结果
     */
    @Override
    public int deleteBizDimissionDistributionById(Integer id)
    {
        return bizDimissionDistributionMapper.deleteBizDimissionDistributionById(id);
    }
}
