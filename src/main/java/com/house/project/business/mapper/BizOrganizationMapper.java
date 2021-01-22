package com.house.project.business.mapper;

import java.util.List;
import com.house.project.business.domain.BizOrganization;
import com.house.project.business.domain.vo.BizOrganizationVo;

/**
 * 机构管理Mapper接口
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
public interface BizOrganizationMapper 
{
    /**
     * 查询机构管理
     * 
     * @param id 机构管理ID
     * @return 机构管理
     */
    public BizOrganizationVo selectBizOrganizationById(Integer id);

    /**
     * 机构编号查询机构管理
     * @param organizationCode 机构编号
     * @return 机构管理
     */
    public BizOrganizationVo selectBizOrganizationByCode(String organizationCode);

    /**
     * 查询机构管理列表
     * 
     * @param bizOrganization 机构管理
     * @return 机构管理集合
     */
    public List<BizOrganizationVo> selectBizOrganizationList(BizOrganization bizOrganization);

    /**
     * 新增机构管理
     * 
     * @param bizOrganization 机构管理
     * @return 结果
     */
    public int insertBizOrganization(BizOrganization bizOrganization);

    /**
     * 修改机构管理
     * 
     * @param bizOrganization 机构管理
     * @return 结果
     */
    public int updateBizOrganization(BizOrganization bizOrganization);

    /**
     * 删除机构管理
     * 
     * @param id 机构管理ID
     * @return 结果
     */
    public int deleteBizOrganizationById(Integer id);

    /**
     * 批量删除机构管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizOrganizationByIds(Integer[] ids);

    /**
     * 批量将机构管理停用
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int updateBizOrganizationDelStatus(Integer[] ids);
}
