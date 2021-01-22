package com.house.project.business.service.impl;

import java.util.List;

import com.house.common.enums.UserStatus;
import com.house.common.exception.CustomException;
import com.house.common.utils.DateUtils;
import com.house.common.utils.SecurityUtils;
import com.house.common.utils.StringUtils;
import com.house.project.business.domain.vo.BizHousesVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.project.business.mapper.BizHousesMapper;
import com.house.project.business.domain.BizHouses;
import com.house.project.business.service.IBizHousesService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 房源管理Service业务层处理
 * 
 */
@Service
public class BizHousesServiceImpl implements IBizHousesService 
{
    private static final Logger log = LoggerFactory.getLogger(BizHousesServiceImpl.class);

    @Autowired
    private BizHousesMapper bizHousesMapper;

    /**
     * 逻辑删除楼盘户型管理
     * @param ids 主键集合
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeHouse(Integer[] ids) {
        BizHouses houses = new BizHouses();
        houses.setStatus(UserStatus.DISABLE.getCode());
        houses.setUpdateBy(SecurityUtils.getUsername());
        for (Integer id : ids) {
            houses.setId(id);
            updateBizHouses(houses);
        }
        return ids.length;
    }

    /**
     * 查询房源管理
     * 
     * @param id 房源管理ID
     * @return 房源管理
     */
    @Override
    public BizHousesVo selectBizHousesById(Integer id)
    {
        return bizHousesMapper.selectBizHousesById(id);
    }

    /**
     * 查询房源管理列表
     * 
     * @param bizHouses 房源管理
     * @return 房源管理
     */
    @Override
    public List<BizHousesVo> selectBizHousesList(BizHouses bizHouses)
    {
        return bizHousesMapper.selectBizHousesList(bizHouses);
    }

    /**
     * 新增房源管理
     * 
     * @param bizHouses 房源管理
     * @return 结果
     */
    @Override
    public int insertBizHouses(BizHouses bizHouses)
    {
        bizHouses.setCreateBy(SecurityUtils.getUsername());
        bizHouses.setCreateTime(DateUtils.getNowDate());
        return bizHousesMapper.insertBizHouses(bizHouses);
    }

    /**
     * 修改房源管理
     * 
     * @param bizHouses 房源管理
     * @return 结果
     */
    @Override
    public int updateBizHouses(BizHouses bizHouses)
    {
        bizHouses.setUpdateBy(SecurityUtils.getUsername());
        bizHouses.setUpdateTime(DateUtils.getNowDate());
        return bizHousesMapper.updateBizHouses(bizHouses);
    }

    /**
     * 批量删除房源管理
     * 
     * @param ids 需要删除的房源管理ID
     * @return 结果
     */
    @Override
    public int deleteBizHousesByIds(Integer[] ids)
    {
        return bizHousesMapper.deleteBizHousesByIds(ids);
    }

    /**
     * 删除房源管理信息
     * 
     * @param id 房源管理ID
     * @return 结果
     */
    @Override
    public int deleteBizHousesById(Integer id)
    {
        return bizHousesMapper.deleteBizHousesById(id);
    }

    /**
     * 导入房源数据
     *
     * @param housesList 房源数据列表
     * @param createBy 操作用户
     * @return 结果
     */
    @Override
    public String importHouses(List<BizHouses> housesList, String createBy)
    {
        if (StringUtils.isNull(housesList) || housesList.size() == 0)
        {
            throw new CustomException("导入数据不能为空！");
        }
        if (StringUtils.isEmpty(createBy)) {
            throw new CustomException("操作用户不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String housesName = null;
        for (BizHouses houses : housesList)
        {
            try
            {
                housesName = (houses.getFloorNo() == null ? "" : houses.getFloorNo()).
                        concat(houses.getUnitNo() == null ? "" : houses.getUnitNo()).
                        concat(houses.getStoreyNo() == null ? "" : houses.getStoreyNo()).
                        concat(houses.getHouseNo() == null ? "" : houses.getHouseNo());
                if (StringUtils.isNull(houses.getProjectId())) {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、楼盘编号为空");
                    continue;
                }
                if (StringUtils.isNull(houses.getProjectHouseTypeId())) {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、楼盘户型编号为空");
                    continue;
                }
                if (StringUtils.isEmpty(houses.getHousesStatus())) {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、房源状态  （1.在售、2.已售、3.售中、4.交付）为空");
                    continue;
                }


                houses.setCreateBy(createBy);
                insertBizHouses(houses);
                successNum++;
                successMsg.append("<br/>").append(successNum).append("、房源 ").append(housesName).append(" 导入成功");
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、房源 " + housesName + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
