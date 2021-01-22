package com.house.project.business.service;

import java.util.List;
import com.house.project.business.domain.BizProject;
import com.house.project.business.domain.vo.BizProjectVo;

/**
 * 楼盘管理Service接口
 * 
 */
public interface IBizProjectService 
{

    /**
     * 逻辑删除楼盘管理和楼盘照片
     * @param ids 楼盘编号集合
     * @return 结果
     */
    public int removeProject(Integer[] ids);

    /**
     * 编辑楼盘管理和楼盘照片
     * @param projectVo 楼盘管理和照片列表
     * @return 结果
     */
    int updateProject(BizProjectVo projectVo);

    /**
     * 添加楼盘管理和楼盘照片
     * @param projectVo 楼盘管理和照片列表
     * @return 结果
     */
    public int addProject(BizProjectVo projectVo);

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
     * 批量删除楼盘管理
     * 
     * @param ids 需要删除的楼盘管理ID
     * @return 结果
     */
    public int deleteBizProjectByIds(Integer[] ids);

    /**
     * 删除楼盘管理信息
     * 
     * @param id 楼盘管理ID
     * @return 结果
     */
    public int deleteBizProjectById(Integer id);
}
