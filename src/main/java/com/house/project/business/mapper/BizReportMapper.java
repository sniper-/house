package com.house.project.business.mapper;

import java.util.List;
import com.house.project.business.domain.BizReport;
import com.house.project.business.domain.BizUser;
import com.house.project.business.domain.vo.BizReportVo;
import org.apache.ibatis.annotations.Param;

/**
 * 报备管理Mapper接口
 *
 * @author bo.zhang
 * @date 2020-07-16
 */
public interface BizReportMapper
{
    /**
     * 到访次数加1
     * @param reportNo 报备编号
     * @return 结果
     */
    public int updateAddVisitTime(String reportNo);

    /**
     * 报备详情查询
     * @param report 查询条件
     * @return 结果
     */
    public List<BizReportVo> selectReportDetail(BizReport report);

    /**
     * 置业顾问编号查询报备信息
     *
     * @param report 参数
     * @return 结果
     */
    public List<BizReportVo> selectByConsultantUsers(BizReport report);

    /**
     * 经纪人编号查询报备信息
     *
     * @param report 参数
     * @return 结果
     */
    public List<BizReportVo> selectByMiddlemanUsers(BizReport report);

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
     * 删除报备管理
     *
     * @param id 报备管理ID
     * @return 结果
     */
    public int deleteBizReportById(Integer id);

    /**
     * 批量删除报备管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizReportByIds(Integer[] ids);

    /**
     * 批量将报备管理停用
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int updateBizReportDelStatus(Integer[] ids);

    /**
     * 更新置业顾问
     * @param newConsultantUserId 新置业顾问id
     * @param originalConsultantUserId 旧置业顾问id
     * @return 结果
     */
    public int updateBizReportConsultant(@Param("newConsultantUserId") Integer newConsultantUserId,
                                        @Param("originalConsultantUserId") Integer originalConsultantUserId,
                                         @Param("reportNo") String reportNo);

    /**
     * 查询对应置业顾问的报备信息
     * @param consultantId 置业顾问编号
     * @return 结果
     */
    public List<BizReportVo> selectByConsultantUserId(Integer consultantId);

    /**
     * 查询报备有效期保护
     * @param report 参数 customerPhone intentionProjectId
     * @return 结果
     */
    public List<BizReport> selectProtectReport(BizReport report);

    /**
     * 报备编号查询
     * @param reportNo 报备编号
     * @return 结果
     */
    public BizReport selectBizReportByReportNo(String reportNo);

    /**
     * 查询当前时间-报备时间 >30天的有效性为0-处理中的报备记录
     * @return
     */
    public List<BizReport> selectOvertimeReport();

    /**
     * 更新当前时间-预约时间 > 2小时并且未到访的报备记录
     * @return
     */
    public int updateOutOfAppointmentTime();

    /**
     * 更新当前时间-报备时间 > 7天的自拓客户报备记录为失效
     */
    public int updateOutOfSelfTime();
}
