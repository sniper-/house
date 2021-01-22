package com.house.project.business.mapper;

import java.util.List;
import com.house.project.business.domain.BizProjectImage;

/**
 * 楼盘照片管理Mapper接口
 * 
 */
public interface BizProjectImageMapper 
{
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
     * 根据楼盘编号修改楼盘照片管理无效
     *
     * @param projectId 楼盘编号
     * @return 结果
     */
    public int updateInvalidBizProjectImage(String projectId);

    /**
     * 删除楼盘照片管理
     * 
     * @param id 楼盘照片管理ID
     * @return 结果
     */
    public int deleteBizProjectImageById(Integer id);

    /**
     * 批量删除楼盘照片管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizProjectImageByIds(Integer[] ids);

    /**
     * 根据楼盘编号删除楼盘照片管理
     *
     * @param projectId 楼盘编号
     * @return 结果
     */
    public int deleteBizProjectImageByProjectId(String projectId);
}
