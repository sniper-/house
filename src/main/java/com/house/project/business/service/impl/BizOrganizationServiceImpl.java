package com.house.project.business.service.impl;

import java.util.List;

import com.house.common.exception.CustomException;
import com.house.common.utils.BaiduUtils;
import com.house.common.utils.DateUtils;
import com.house.common.utils.SecurityUtils;
import com.house.common.utils.StringUtils;
import com.house.project.business.domain.vo.BizOrganizationVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.project.business.mapper.BizOrganizationMapper;
import com.house.project.business.domain.BizOrganization;
import com.house.project.business.service.IBizOrganizationService;

/**
 * 机构管理Service业务层处理
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
@Service
public class BizOrganizationServiceImpl implements IBizOrganizationService 
{

    private static final Logger log = LoggerFactory.getLogger(BizOrganizationServiceImpl.class);

    @Autowired
    private BizOrganizationMapper bizOrganizationMapper;

    @Override
    public int updateBizOrganizationDelStatus(Integer[] ids) {
        return bizOrganizationMapper.updateBizOrganizationDelStatus(ids);
    }

    @Override
    public BizOrganizationVo selectBizOrganizationByCode(String organizationCode) {
        return bizOrganizationMapper.selectBizOrganizationByCode(organizationCode);
    }

    /**
     * 查询机构管理
     * 
     * @param id 机构管理ID
     * @return 机构管理
     */
    @Override
    public BizOrganizationVo selectBizOrganizationById(Integer id)
    {
        return bizOrganizationMapper.selectBizOrganizationById(id);
    }

    /**
     * 查询机构管理列表
     * 
     * @param bizOrganization 机构管理
     * @return 机构管理
     */
    @Override
    public List<BizOrganizationVo> selectBizOrganizationList(BizOrganization bizOrganization)
    {
        return bizOrganizationMapper.selectBizOrganizationList(bizOrganization);
    }

    /**
     * 新增机构管理
     * 
     * @param bizOrganization 机构管理
     * @return 结果
     */
    @Override
    public int insertBizOrganization(BizOrganization bizOrganization)
    {
        // 设置经纬度
        validateLocation(bizOrganization);
        // 创建人默认登录用户
        if (StringUtils.isEmpty(bizOrganization.getCreateBy())) {
            bizOrganization.setCreateBy(SecurityUtils.getUsername());
        }
        bizOrganization.setCreateTime(DateUtils.getNowDate());
        return bizOrganizationMapper.insertBizOrganization(bizOrganization);
    }

    /**
     * 修改机构管理
     * 
     * @param bizOrganization 机构管理
     * @return 结果
     */
    @Override
    public int updateBizOrganization(BizOrganization bizOrganization)
    {
        // 设置经纬度
        validateLocation(bizOrganization);
        // 创建人默认登录用户
        if (StringUtils.isEmpty(bizOrganization.getUpdateBy())) {
            bizOrganization.setUpdateBy(SecurityUtils.getUsername());
        }
        bizOrganization.setUpdateTime(DateUtils.getNowDate());
        return bizOrganizationMapper.updateBizOrganization(bizOrganization);
    }

    /**
     * 批量删除机构管理
     * 
     * @param ids 需要删除的机构管理ID
     * @return 结果
     */
    @Override
    public int deleteBizOrganizationByIds(Integer[] ids)
    {
        return bizOrganizationMapper.deleteBizOrganizationByIds(ids);
    }

    /**
     * 删除机构管理信息
     * 
     * @param id 机构管理ID
     * @return 结果
     */
    @Override
    public int deleteBizOrganizationById(Integer id)
    {
        return bizOrganizationMapper.deleteBizOrganizationById(id);
    }

    /**
     * 导入机构数据
     *
     * @param organizationList 机构数据列表
     * @param createBy 操作用户
     * @return 结果
     */
    @Override
    public String importOrganization(List<BizOrganization> organizationList, String createBy)
    {
        if (StringUtils.isNull(organizationList) || organizationList.size() == 0)
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
        BizOrganization param = new BizOrganization();
        for (BizOrganization organization : organizationList)
        {
            try
            {
                if (StringUtils.isEmpty(organization.getOrganizationName())) {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、机构名称为空");
                    continue;
                }
                param.setOrganizationName(organization.getOrganizationName());
                // 验证是否存在这个机构名称
                List<BizOrganizationVo> bizOrganizations = bizOrganizationMapper.selectBizOrganizationList(param);

                if (StringUtils.isEmpty(bizOrganizations))
                {
                    organization.setCreateBy(createBy);
                    insertBizOrganization(organization);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、机构名称 ").append(organization.getOrganizationName()).append(" 导入成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、机构名称 ").append(organization.getOrganizationName()).append(" 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、机构名称 " + organization.getOrganizationName() + " 导入失败：";
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

    private void validateLocation(BizOrganization organization) {
        // 根据地址获得经纬度
        if (!StringUtils.isEmpty(organization.getOrganizationAddress())) {
            BaiduUtils.Location location = BaiduUtils.getLatitude(organization.getOrganizationAddress());
            if (location == null) {
                throw new CustomException("地址不正确, 请确认地址");
            }
            organization.setOrganizationAddressLongitude(location.getLongitude()); //经度
            organization.setOrganizationAddressLatitude(location.getLatitude());  //纬度
        }
    }
}
