package com.house.project.business.mapper;

import java.util.List;
import com.house.project.business.domain.BizProject;

/**
 * 楼盘管理Mapper接口
 * 
 */
public interface BizProjectMapper 
{
    /**
     * 查询楼盘管理
     * 
     * @param id 楼盘管理ID
     * @return 楼盘管理
     */
    public BizProject selectBizProjectById(Integer id);

    /**
     * 查询楼盘管理列表
     * 
     * @param bizProject 楼盘管理
     * @return 楼盘管理集合
     */
    public List<BizProject> selectBizProjectList(BizProject bizProject);

    /**
     * 新增楼盘管理
     * 
     * @param bizProject 楼盘管理
     * @return 结果
     */
    public int insertBizProject(BizProject bizProject);

    /**
     * 修改楼盘管理
     * 
     * @param bizProject 楼盘管理
     * @return 结果
     */
    public int updateBizProject(BizProject bizProject);

    /**
     * 删除楼盘管理
     * 
     * @param id 楼盘管理ID
     * @return 结果
     */
    public int deleteBizProjectById(Integer id);

    /**
     * 批量删除楼盘管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizProjectByIds(Integer[] ids);
}
