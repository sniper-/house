package com.house.project.business.service;

import java.util.List;
import com.house.project.business.domain.BizDimissionDistribution;

/**
 * 离职分配Service接口
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
public interface IBizDimissionDistributionService 
{
    /**
     * 查询离职分配
     * 
     * @param id 离职分配ID
     * @return 离职分配
     */
    public BizDimissionDistribution selectBizDimissionDistributionById(Integer id);

    /**
     * 查询离职分配列表
     * 
     * @param bizDimissionDistribution 离职分配
     * @return 离职分配集合
     */
    public List<BizDimissionDistribution> selectBizDimissionDistributionList(BizDimissionDistribution bizDimissionDistribution);

    /**
     * 新增离职分配
     * 
     * @param bizDimissionDistribution 离职分配
     * @return 结果
     */
    public int insertBizDimissionDistribution(BizDimissionDistribution bizDimissionDistribution);

    /**
     * 修改离职分配
     * 
     * @param bizDimissionDistribution 离职分配
     * @return 结果
     */
    public int updateBizDimissionDistribution(BizDimissionDistribution bizDimissionDistribution);

    /**
     * 批量删除离职分配
     * 
     * @param ids 需要删除的离职分配ID
     * @return 结果
     */
    public int deleteBizDimissionDistributionByIds(Integer[] ids);

    /**
     * 删除离职分配信息
     * 
     * @param id 离职分配ID
     * @return 结果
     */
    public int deleteBizDimissionDistributionById(Integer id);
}
