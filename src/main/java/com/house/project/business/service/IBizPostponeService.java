package com.house.project.business.service;

import java.util.List;
import com.house.project.business.domain.BizPostpone;
import com.house.project.business.domain.BizReport;

/**
 * 延期申请Service接口
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
public interface IBizPostponeService 
{

    /**
     * 查询待审核的延期申请
     * @param reportNo 报备编号
     * @return 结果
     */
    public BizPostpone selectNotApproveData(String reportNo);

    /**
     * 报备信息延期新增
     * @param reportId 报备id
     * @param postpone 延期申请信息
     * @return 结果
     */
    public int addPostpone(Integer reportId, BizPostpone postpone);

    /**
     * 审核信息延期新增
     * @param report 报备
     * @param postpone 延期申请
     * @return 结果
     */
    public int approve(BizReport report, BizPostpone postpone);

    /**
     * 查询延期申请
     * 
     * @param id 延期申请ID
     * @return 延期申请
     */
    public BizPostpone selectBizPostponeById(Integer id);

    /**
     * 查询延期申请列表
     * 
     * @param bizPostpone 延期申请
     * @return 延期申请集合
     */
    public List<BizPostpone> selectBizPostponeList(BizPostpone bizPostpone);

    /**
     * 新增延期申请
     * 
     * @param bizPostpone 延期申请
     * @return 结果
     */
    public int insertBizPostpone(BizPostpone bizPostpone);

    /**
     * 修改延期申请
     * 
     * @param bizPostpone 延期申请
     * @return 结果
     */
    public int updateBizPostpone(BizPostpone bizPostpone);

    /**
     * 批量删除延期申请
     * 
     * @param ids 需要删除的延期申请ID
     * @return 结果
     */
    public int deleteBizPostponeByIds(Integer[] ids);

    /**
     * 删除延期申请信息
     * 
     * @param id 延期申请ID
     * @return 结果
     */
    public int deleteBizPostponeById(Integer id);
}
