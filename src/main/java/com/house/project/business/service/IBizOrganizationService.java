package com.house.project.business.service;

import java.util.List;
import com.house.project.business.domain.BizOrganization;
import com.house.project.business.domain.vo.BizOrganizationVo;

/**
 * 机构管理Service接口
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
public interface IBizOrganizationService
{
    /**
     * 批量将机构管理停用
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int updateBizOrganizationDelStatus(Integer[] ids);

    /**
     * 机构编号查询机构管理
     * @param organizationCode 机构编号
     * @return 机构管理
     */
    public BizOrganizationVo selectBizOrganizationByCode(String organizationCode);

    /**
     * 查询机构管理
     * 
     * @param id 机构管理ID
     * @return 机构管理
     */
    public BizOrganizationVo selectBizOrganizationById(Integer id);

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
     * 批量删除机构管理
     * 
     * @param ids 需要删除的机构管理ID
     * @return 结果
     */
    public int deleteBizOrganizationByIds(Integer[] ids);

    /**
     * 删除机构管理信息
     * 
     * @param id 机构管理ID
     * @return 结果
     */
    public int deleteBizOrganizationById(Integer id);

    /**
     * 导入机构数据
     *
     * @param organizationList 机构数据列表
     * @param createBy 操作用户
     * @return 结果
     */
    public String importOrganization(List<BizOrganization> organizationList, String createBy);
}
