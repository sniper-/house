package com.house.project.business.mapper;

import java.util.List;
import com.house.project.business.domain.BizProjectHouseType;
import com.house.project.business.domain.vo.BizProjectHouseTypeVo;

/**
 * 楼盘户型管理Mapper接口
 * 
 */
public interface BizProjectHouseTypeMapper 
{
    /**
     * 查询楼盘户型管理
     * 
     * @param id 楼盘户型管理ID
     * @return 楼盘户型管理
     */
    public BizProjectHouseType selectBizProjectHouseTypeById(Integer id);

    /**
     * 查询楼盘户型管理列表
     * 
     * @param vo 楼盘户型管理
     * @return 楼盘户型管理集合
     */
    public List<BizProjectHouseType> selectBizProjectHouseTypeList(BizProjectHouseTypeVo vo);

    /**
     * 新增楼盘户型管理
     * 
     * @param bizProjectHouseType 楼盘户型管理
     * @return 结果
     */
    public int insertBizProjectHouseType(BizProjectHouseType bizProjectHouseType);

    /**
     * 修改楼盘户型管理
     * 
     * @param bizProjectHouseType 楼盘户型管理
     * @return 结果
     */
    public int updateBizProjectHouseType(BizProjectHouseType bizProjectHouseType);

    /**
     * 删除楼盘户型管理
     * 
     * @param id 楼盘户型管理ID
     * @return 结果
     */
    public int deleteBizProjectHouseTypeById(Integer id);

    /**
     * 批量删除楼盘户型管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizProjectHouseTypeByIds(Integer[] ids);
}
