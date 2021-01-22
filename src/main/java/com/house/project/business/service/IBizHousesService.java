package com.house.project.business.service;

import java.util.List;
import com.house.project.business.domain.BizHouses;
import com.house.project.business.domain.vo.BizHousesVo;

/**
 * 房源管理Service接口
 * 
 */
public interface IBizHousesService 
{

    /**
     * 逻辑删除楼盘户型管理
     * @param ids 主键集合
     * @return 结果
     */
    public int removeHouse(Integer[] ids);

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
     * 批量删除房源管理
     * 
     * @param ids 需要删除的房源管理ID
     * @return 结果
     */
    public int deleteBizHousesByIds(Integer[] ids);

    /**
     * 删除房源管理信息
     * 
     * @param id 房源管理ID
     * @return 结果
     */
    public int deleteBizHousesById(Integer id);

    /**
     * 导入房源数据
     *
     * @param housesList 房源数据列表
     * @param createBy 操作用户
     * @return 结果
     */
    public String importHouses(List<BizHouses> housesList, String createBy);
}
