package com.house.project.business.service;

import java.util.List;
import com.house.project.business.domain.BizCollect;
import com.house.project.business.domain.vo.BizCollectVo;

/**
 * 统计管理Service接口
 * 
 * @author bo.zhang
 * @date 2020-08-12
 */
public interface IBizCollectService 
{

    /**
     * 报备统计
     * @param purchasedProjectId 成交楼盘编号
     * @param userId 用户id
     * @return 结果
     */
    public BizCollectVo queryCollect(Integer purchasedProjectId, Long userId);


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
     * 批量删除统计管理
     * 
     * @param ids 需要删除的统计管理ID
     * @return 结果
     */
    public int deleteBizCollectByIds(Integer[] ids);

    /**
     * 删除统计管理信息
     * 
     * @param id 统计管理ID
     * @return 结果
     */
    public int deleteBizCollectById(Integer id);
}
