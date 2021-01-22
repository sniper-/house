package com.house.project.business.service;

import java.util.List;
import com.house.project.business.domain.BizProjectImage;

/**
 * 楼盘照片管理Service接口
 * 
 */
public interface IBizProjectImageService 
{
    /**
     * 根据楼盘编号修改楼盘照片管理无效
     *
     * @param projectId 楼盘编号
     * @return 结果
     */
    public int updateInvalidBizProjectImage(String projectId);

    /**
     * 根据楼盘编号删除楼盘照片管理
     *
     * @param projectId 楼盘编号
     * @return 结果
     */
    public int deleteBizProjectImageByProjectId(String projectId);

    /**
     * 批量新增楼盘照片
     * @param projectImages 楼盘照片列表
     * @param projectId 楼盘id
     */
    public void addProjectImages(List<BizProjectImage> projectImages, String projectId);

    /**
     * 查询楼盘照片管理
     * 
     * @param id 楼盘照片管理ID
     * @return 楼盘照片管理
     */
    public BizProjectImage selectBizProjectImageById(Integer id);

    /**
     * 查询楼盘照片管理列表
     * 
     * @param bizProjectImage 楼盘照片管理
     * @return 楼盘照片管理集合
     */
    public List<BizProjectImage> selectBizProjectImageList(BizProjectImage bizProjectImage);

    /**
     * 新增楼盘照片管理
     * 
     * @param bizProjectImage 楼盘照片管理
     * @return 结果
     */
    public int insertBizProjectImage(BizProjectImage bizProjectImage);

    /**
     * 修改楼盘照片管理
     * 
     * @param bizProjectImage 楼盘照片管理
     * @return 结果
     */
    public int updateBizProjectImage(BizProjectImage bizProjectImage);

    /**
     * 批量删除楼盘照片管理
     * 
     * @param ids 需要删除的楼盘照片管理ID
     * @return 结果
     */
    public int deleteBizProjectImageByIds(Integer[] ids);

    /**
     * 删除楼盘照片管理信息
     * 
     * @param id 楼盘照片管理ID
     * @return 结果
     */
    public int deleteBizProjectImageById(Integer id);
}
