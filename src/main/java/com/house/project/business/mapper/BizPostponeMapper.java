package com.house.project.business.mapper;

import java.util.List;
import com.house.project.business.domain.BizPostpone;

/**
 * 延期申请Mapper接口
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
public interface BizPostponeMapper 
{

    /**
     * 查询待审核的延期申请
     * @param reportNo 报备编号
     * @return 结果
     */
    public BizPostpone selectNotApproveData(String reportNo);

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
     * 删除延期申请
     * 
     * @param id 延期申请ID
     * @return 结果
     */
    public int deleteBizPostponeById(Integer id);

    /**
     * 批量删除延期申请
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizPostponeByIds(Integer[] ids);
}
