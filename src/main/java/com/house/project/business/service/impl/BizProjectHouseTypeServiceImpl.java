package com.house.project.business.service.impl;

import java.util.List;

import com.house.common.enums.UserStatus;
import com.house.common.utils.DateUtils;
import com.house.common.utils.SecurityUtils;
import com.house.project.business.domain.vo.BizProjectHouseTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.project.business.mapper.BizProjectHouseTypeMapper;
import com.house.project.business.domain.BizProjectHouseType;
import com.house.project.business.service.IBizProjectHouseTypeService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 楼盘户型管理Service业务层处理
 * 
 */
@Service
public class BizProjectHouseTypeServiceImpl implements IBizProjectHouseTypeService 
{
    @Autowired
    private BizProjectHouseTypeMapper bizProjectHouseTypeMapper;

    /**
     * 逻辑删除楼盘户型管理
     * @param ids 主键集合
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeProjectHouseType(Integer[] ids) {
        BizProjectHouseType type = new BizProjectHouseType();
        type.setStatus(UserStatus.DISABLE.getCode());
        type.setUpdateBy(SecurityUtils.getUsername());
        for (Integer id : ids) {
            type.setId(id);
            updateBizProjectHouseType(type);
        }
        return ids.length;
    }

    /**
     * 查询楼盘户型管理
     * 
     * @param id 楼盘户型管理ID
     * @return 楼盘户型管理
     */
    @Override
    public BizProjectHouseType selectBizProjectHouseTypeById(Integer id)
    {
        return bizProjectHouseTypeMapper.selectBizProjectHouseTypeById(id);
    }

    /**
     * 查询楼盘户型管理列表
     * 
     * @param vo 楼盘户型管理
     * @return 楼盘户型管理
     */
    @Override
    public List<BizProjectHouseType> selectBizProjectHouseTypeList(BizProjectHouseTypeVo vo)
    {
        return bizProjectHouseTypeMapper.selectBizProjectHouseTypeList(vo);
    }

    /**
     * 新增楼盘户型管理
     * 
     * @param bizProjectHouseType 楼盘户型管理
     * @return 结果
     */
    @Override
    public int insertBizProjectHouseType(BizProjectHouseType bizProjectHouseType)
    {
        bizProjectHouseType.setCreateBy(SecurityUtils.getUsername());
        bizProjectHouseType.setCreateTime(DateUtils.getNowDate());
        return bizProjectHouseTypeMapper.insertBizProjectHouseType(bizProjectHouseType);
    }

    /**
     * 修改楼盘户型管理
     * 
     * @param bizProjectHouseType 楼盘户型管理
     * @return 结果
     */
    @Override
    public int updateBizProjectHouseType(BizProjectHouseType bizProjectHouseType)
    {
        bizProjectHouseType.setUpdateBy(SecurityUtils.getUsername());
        bizProjectHouseType.setUpdateTime(DateUtils.getNowDate());
        return bizProjectHouseTypeMapper.updateBizProjectHouseType(bizProjectHouseType);
    }

    /**
     * 批量删除楼盘户型管理
     * 
     * @param ids 需要删除的楼盘户型管理ID
     * @return 结果
     */
    @Override
    public int deleteBizProjectHouseTypeByIds(Integer[] ids)
    {
        return bizProjectHouseTypeMapper.deleteBizProjectHouseTypeByIds(ids);
    }

    /**
     * 删除楼盘户型管理信息
     * 
     * @param id 楼盘户型管理ID
     * @return 结果
     */
    @Override
    public int deleteBizProjectHouseTypeById(Integer id)
    {
        return bizProjectHouseTypeMapper.deleteBizProjectHouseTypeById(id);
    }
}
