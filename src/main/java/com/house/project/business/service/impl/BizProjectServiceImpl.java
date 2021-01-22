package com.house.project.business.service.impl;

import java.util.List;

import com.house.common.enums.UserStatus;
import com.house.common.utils.DateUtils;
import com.house.common.utils.SecurityUtils;
import com.house.project.business.domain.BizProjectImage;
import com.house.project.business.domain.vo.BizProjectVo;
import com.house.project.business.service.IBizProjectImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.project.business.mapper.BizProjectMapper;
import com.house.project.business.domain.BizProject;
import com.house.project.business.service.IBizProjectService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 楼盘管理Service业务层处理
 * 
 */
@Service
public class BizProjectServiceImpl implements IBizProjectService 
{
    @Autowired
    private BizProjectMapper bizProjectMapper;
    @Autowired
    private IBizProjectImageService projectImageService;

    /**
     * 逻辑删除楼盘管理和楼盘照片
     * @param ids 楼盘编号集合
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeProject(Integer[] ids) {
        BizProject project = new BizProject();
        project.setStatus(UserStatus.DISABLE.getCode());
        project.setUpdateBy(SecurityUtils.getUsername());
        for (Integer id : ids) {
            // 逻辑删除楼盘
            project.setId(id);
            updateBizProject(project);
            // 逻辑删除楼盘照片
            projectImageService.updateInvalidBizProjectImage(id.toString());
        }
        return ids.length;
    }

    /**
     * 编辑楼盘管理和楼盘照片
     * @param projectVo 楼盘管理和照片列表
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateProject(BizProjectVo projectVo) {
        BizProject project = projectVo.getProject();
        project.setUpdateBy(SecurityUtils.getUsername());
        int result = updateBizProject(project);// 修改楼盘
        projectImageService.deleteBizProjectImageByProjectId(project.getId().toString()); //删除原有的楼盘照片
        List<BizProjectImage> projectImages = projectVo.getProjectImages();
        // 新增楼盘照片
        projectImageService.addProjectImages(projectImages, project.getId().toString());
        return result;
    }

    /**
     * 添加楼盘管理和楼盘照片
     * @param projectVo 楼盘管理和照片列表
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addProject(BizProjectVo projectVo) {
        BizProject project = projectVo.getProject();
        project.setCreateBy(SecurityUtils.getUsername());
        int result = bizProjectMapper.insertBizProject(project);
        List<BizProjectImage> projectImages = projectVo.getProjectImages();
        // 新增楼盘照片
        projectImageService.addProjectImages(projectImages, project.getId().toString());
        return result;
    }

    /**
     * 查询楼盘管理
     * 
     * @param id 楼盘管理ID
     * @return 楼盘管理
     */
    @Override
    public BizProject selectBizProjectById(Integer id)
    {
        return bizProjectMapper.selectBizProjectById(id);
    }

    /**
     * 查询楼盘管理列表
     * 
     * @param bizProject 楼盘管理
     * @return 楼盘管理
     */
    @Override
    public List<BizProject> selectBizProjectList(BizProject bizProject)
    {
        return bizProjectMapper.selectBizProjectList(bizProject);
    }

    /**
     * 新增楼盘管理
     * 
     * @param bizProject 楼盘管理
     * @return 结果
     */
    @Override
    public int insertBizProject(BizProject bizProject)
    {
        bizProject.setCreateTime(DateUtils.getNowDate());
        return bizProjectMapper.insertBizProject(bizProject);
    }

    /**
     * 修改楼盘管理
     * 
     * @param bizProject 楼盘管理
     * @return 结果
     */
    @Override
    public int updateBizProject(BizProject bizProject)
    {
        bizProject.setUpdateTime(DateUtils.getNowDate());
        return bizProjectMapper.updateBizProject(bizProject);
    }

    /**
     * 批量删除楼盘管理
     * 
     * @param ids 需要删除的楼盘管理ID
     * @return 结果
     */
    @Override
    public int deleteBizProjectByIds(Integer[] ids)
    {
        return bizProjectMapper.deleteBizProjectByIds(ids);
    }

    /**
     * 删除楼盘管理信息
     * 
     * @param id 楼盘管理ID
     * @return 结果
     */
    @Override
    public int deleteBizProjectById(Integer id)
    {
        return bizProjectMapper.deleteBizProjectById(id);
    }
}
