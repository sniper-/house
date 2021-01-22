package com.house.project.business.service.impl;

import java.util.List;
import com.house.common.utils.DateUtils;
import com.house.common.utils.SecurityUtils;
import com.house.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.project.business.mapper.BizProjectImageMapper;
import com.house.project.business.domain.BizProjectImage;
import com.house.project.business.service.IBizProjectImageService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 楼盘照片管理Service业务层处理
 * 
 */
@Service
public class BizProjectImageServiceImpl implements IBizProjectImageService 
{
    @Autowired
    private BizProjectImageMapper bizProjectImageMapper;

    @Override
    public int updateInvalidBizProjectImage(String projectId) {
        return bizProjectImageMapper.updateInvalidBizProjectImage(projectId);
    }

    @Override
    public int deleteBizProjectImageByProjectId(String projectId) {
        return bizProjectImageMapper.deleteBizProjectImageByProjectId(projectId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addProjectImages(List<BizProjectImage> projectImages, String projectId) {
        // 新增楼盘照片
        if (StringUtils.isEmpty(projectImages)) {
            return;
        }
        projectImages.forEach(image -> {
            image.setProjectId(projectId);
            image.setCreateBy(SecurityUtils.getUsername());
            image.setUpdateBy(SecurityUtils.getUsername());
            bizProjectImageMapper.insertBizProjectImage(image);
        });
    }

    /**
     * 查询楼盘照片管理
     * 
     * @param id 楼盘照片管理ID
     * @return 楼盘照片管理
     */
    @Override
    public BizProjectImage selectBizProjectImageById(Integer id)
    {
        return bizProjectImageMapper.selectBizProjectImageById(id);
    }

    /**
     * 查询楼盘照片管理列表
     * 
     * @param bizProjectImage 楼盘照片管理
     * @return 楼盘照片管理
     */
    @Override
    public List<BizProjectImage> selectBizProjectImageList(BizProjectImage bizProjectImage)
    {
        return bizProjectImageMapper.selectBizProjectImageList(bizProjectImage);
    }

    /**
     * 新增楼盘照片管理
     * 
     * @param bizProjectImage 楼盘照片管理
     * @return 结果
     */
    @Override
    public int insertBizProjectImage(BizProjectImage bizProjectImage)
    {
        bizProjectImage.setCreateTime(DateUtils.getNowDate());
        return bizProjectImageMapper.insertBizProjectImage(bizProjectImage);
    }

    /**
     * 修改楼盘照片管理
     * 
     * @param bizProjectImage 楼盘照片管理
     * @return 结果
     */
    @Override
    public int updateBizProjectImage(BizProjectImage bizProjectImage)
    {
        bizProjectImage.setUpdateTime(DateUtils.getNowDate());
        return bizProjectImageMapper.updateBizProjectImage(bizProjectImage);
    }

    /**
     * 批量删除楼盘照片管理
     * 
     * @param ids 需要删除的楼盘照片管理ID
     * @return 结果
     */
    @Override
    public int deleteBizProjectImageByIds(Integer[] ids)
    {
        return bizProjectImageMapper.deleteBizProjectImageByIds(ids);
    }

    /**
     * 删除楼盘照片管理信息
     * 
     * @param id 楼盘照片管理ID
     * @return 结果
     */
    @Override
    public int deleteBizProjectImageById(Integer id)
    {
        return bizProjectImageMapper.deleteBizProjectImageById(id);
    }
}
