package com.house.project.business.service;

import java.util.List;

import com.house.project.business.domain.BizDimissionDistribution;
import com.house.project.business.domain.BizHouses;
import com.house.project.business.domain.BizReport;
import com.house.project.business.domain.BizUser;
import com.house.project.business.domain.vo.BizReportVo;
import com.house.project.system.domain.SysRole;

/**
 * 报备管理Service接口
 *
 * @author bo.zhang
 * @date 2020-07-16
 */
public interface IBizReportService
{

    /**
     * 更新当前时间-报备时间 > 24小时并且未到访的报备记录
     * @return 结果
     */
    public int updateOutOfAppointmentTime();

    /**
     * 更新当前时间-报备时间 > 7天的自拓客户报备记录为失效
     */
    public int updateOutOfSelfTime();

    /**
     * 更新报备管理
     *
     * @param bizReport 报备管理
     * @return 结果
     */
    public int editReport(BizReport bizReport);

    /**
     * 根据部门号查询业务用户部分下的所有用户id
     * @param user 业务用户
     * @return 结果
     */
    public Integer[] queryUserIdsByOrg(BizUser user);

    /**
     * 查询H5管理列表
     * @param report 报备查询条件
     * @param user 业务用户
     * @return 结果
     */
    public List<BizReportVo> selectH5ReportList(BizReport report, BizUser user);

    /**
     * 查询渠道专员报备管理列表
     * @param report 报备查询条件
     * @return 结果
     */
    public List<BizReportVo> selectWebReportList(BizReport report, List<SysRole> sysRoles);

    /**
     * 查询当前时间-报备时间 >30天的有效性为0-处理中的报备记录
     * @return
     */
    public List<BizReport> selectOvertimeReport();

    /**
     * 报备编号查询
     * @param reportNo 报备编号
     * @return 结果
     */
    public BizReport selectBizReportByReportNo(String reportNo);

    /**
     * 查询报备有效期保护
     * @param report 参数 customerPhone intentionProjectId
     * @return 结果
     */
    public List<BizReport> selectProtectReport(BizReport report);

    /**
     * 查询对应置业顾问的报备信息
     * @param consultantId 置业顾问编号
     * @return 结果
     */
    public List<BizReportVo> selectByConsultantUserId(Integer consultantId);

    /**
     * 置业顾问离职重新分配报备信息, 修改置业顾问id
     * @param list 离职分配列表
     * @return 结果
     */
    public int distribution(List<BizDimissionDistribution> list);

    /**
     * 批量将报备管理停用
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int updateBizReportDelStatus(Integer[] ids);

    /**
     * 查询报备管理
     *
     * @param id 报备管理ID
     * @return 报备管理
     */
    public BizReportVo selectBizReportById(Integer id);

    /**
     * 查询报备管理列表
     *
     * @param bizReport 报备管理
     * @return 报备管理集合
     */
    public List<BizReport> selectBizReportList(BizReport bizReport);

    /**
     * 新增报备管理
     *
     * @param bizReport 报备管理
     * @return 结果
     */
    public int insertBizReport(BizReport bizReport);

    /**
     * 修改报备管理
     *
     * @param bizReport 报备管理
     * @return 结果
     */
    public int updateBizReport(BizReport bizReport);

    /**
     * 批量删除报备管理
     *
     * @param ids 需要删除的报备管理ID
     * @return 结果
     */
    public int deleteBizReportByIds(Integer[] ids);

    /**
     * 删除报备管理信息
     *
     * @param id 报备管理ID
     * @return 结果
     */
    public int deleteBizReportById(Integer id);

    /**
     * 导入房源数据
     *
     * @param reportList 房源数据列表
     * @param createBy 操作用户
     * @return 结果
     */
    public String importReports(List<BizReport> reportList, String createBy);
}
