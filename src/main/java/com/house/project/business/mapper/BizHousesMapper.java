package com.house.project.business.mapper;

import java.util.List;
import com.house.project.business.domain.BizHouses;
import com.house.project.business.domain.vo.BizHousesVo;

/**
 * 房源管理Mapper接口
 * 
 */
public interface BizHousesMapper 
{
    /**
     * 查询房源管理
     * 
     * @param id 房源管理ID
     * @return 房源管理
     */
    public BizHousesVo selectBizHousesById(Integer id);

    /**
     * 查询房源管理列表
     * 
     * @param bizHouses 房源管理
     * @return 房源管理集合
     */
    public List<BizHousesVo> selectBizHousesList(BizHouses bizHouses);

    /**
     * 新增房源管理
     * 
     * @param bizHouses 房源管理
     * @return 结果
     */
    public int insertBizHouses(BizHouses bizHouses);

    /**
     * 修改房源管理
     * 
     * @param bizHouses 房源管理
     * @return 结果
     */
    public int updateBizHouses(BizHouses bizHouses);

    /**
     * 删除房源管理
     * 
     * @param id 房源管理ID
     * @return 结果
     */
    public int deleteBizHousesById(Integer id);

    /**
     * 批量删除房源管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizHousesByIds(Integer[] ids);
}
