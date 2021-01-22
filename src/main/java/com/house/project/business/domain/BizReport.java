package com.house.project.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.house.framework.aspectj.lang.annotation.Excel;
import com.house.framework.web.domain.BaseEntity;

/**
 * 报备管理对象 biz_report
 *
 * @author bo.zhang
 * @date 2020-07-16
 */
public class BizReport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增 */
    private Integer id;

    /** 报备编号 */
    @Excel(name = "报备编号")
    private String reportNo;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Integer userId;

    /** 客户姓名 */
    @Excel(name = "客户姓名")
    private String customerName;

    /** 客户手机号 */
    @Excel(name = "客户手机号")
    private String customerPhone;

    /** 客户备用手机号 */
    @Excel(name = "客户备用手机号")
    private String customerBackupPhone;

    /** 客户备用手机号 */
    @Excel(name = "客户备用手机号3")
    private String customerBackupPhone3;

    /** 意向楼盘编号 */
    private Integer intentionProjectId;

    /** 服务商编号 */
    private Long deptId;

    /** 意向房源 */
    @Excel(name = "意向房源")
    private String intentionHouses;

    /** 成交楼盘编号 */
    private Integer purchasedProjectId;

    /** 成交房源编号 */
    private Integer purchasedHousesId;

    /** 经纪人编号 */
    private Integer middlemanUserId;

    /** 报备时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Excel(name = "报备时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reportTime;

    /** 预约时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Excel(name = "预约时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date appointmentTime;

    /** 报备有效时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Excel(name = "报备有效时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reportValidityTime;

    /** 置业顾问编号 */
    @Excel(name = "置业顾问编号")
    private Integer consultantUserId;

    /** 渠道专员编号sys_user.user_id */
    @Excel(name = "渠道专员编号")
    private Long channelSysUserId;

    /** 服务商编号sys_dept.dept_id */
    private Long serviceProviderId;

    /** 首次到访时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Excel(name = "首次到访时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date firstVisitTime;

    /** 到访次数 */
    @Excel(name = "到访次数")
    private Integer visitTimes;

    /** 客户评级  */
    @Excel(name = "客户评级 ")
    private String customerRating;

    /** 延期申请状态  0- 未申请  1-待审核  2-已审核 */
    @Excel(name = "延期申请状态", readConverterExp = "0=未申请,1=待审核,2=已审核")
    private String postponeStatus;

    /** 报备状态  0-未到访 1-已到访 2-已交认筹金 3-已交意向金 4-已交定金 5-首付已交齐 6-已网签 7-已办理贷款 8-已全款 9-已交房 10-已办理房产证 */
    @Excel(name = "报备状态", readConverterExp = "0=未到访,1=已到访,2=已交认筹金,3=已交意向金,4=已交定金,5=首付已交齐,6=已网签,7=已办理贷款,8=已全款,9=已交房,10=已办理房产证")
    private String reportStatus;

    /** 报备有效性 0-处理中 1-无效 2-有效 */
    @Excel(name = "报备有效性", readConverterExp = "0=处理中,1=无效,2=有效")
    private String validStatus;

    /** 状态（0正常 1停用） */
    private String status;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setReportNo(String reportNo)
    {
        this.reportNo = reportNo;
    }

    public String getReportNo()
    {
        return reportNo;
    }
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public Integer getUserId()
    {
        return userId;
    }
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getCustomerName()
    {
        return customerName;
    }
    public void setCustomerPhone(String customerPhone)
    {
        this.customerPhone = customerPhone;
    }

    public String getCustomerPhone()
    {
        return customerPhone;
    }
    public void setIntentionProjectId(Integer intentionProjectId)
    {
        this.intentionProjectId = intentionProjectId;
    }

    public String getCustomerBackupPhone() {
        return customerBackupPhone;
    }

    public void setCustomerBackupPhone(String customerBackupPhone) {
        this.customerBackupPhone = customerBackupPhone;
    }

    public String getCustomerBackupPhone3() {
        return customerBackupPhone3;
    }

    public void setCustomerBackupPhone3(String customerBackupPhone3) {
        this.customerBackupPhone3 = customerBackupPhone3;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Integer getIntentionProjectId()
    {
        return intentionProjectId;
    }
    public void setIntentionHouses(String intentionHouses)
    {
        this.intentionHouses = intentionHouses;
    }

    public String getIntentionHouses()
    {
        return intentionHouses;
    }
    public void setPurchasedProjectId(Integer purchasedProjectId)
    {
        this.purchasedProjectId = purchasedProjectId;
    }

    public Integer getPurchasedProjectId()
    {
        return purchasedProjectId;
    }
    public void setPurchasedHousesId(Integer purchasedHousesId)
    {
        this.purchasedHousesId = purchasedHousesId;
    }

    public Integer getPurchasedHousesId()
    {
        return purchasedHousesId;
    }
    public void setMiddlemanUserId(Integer middlemanUserId)
    {
        this.middlemanUserId = middlemanUserId;
    }

    public Integer getMiddlemanUserId()
    {
        return middlemanUserId;
    }
    public void setReportTime(Date reportTime)
    {
        this.reportTime = reportTime;
    }

    public Date getReportTime()
    {
        return reportTime;
    }
    public void setAppointmentTime(Date appointmentTime)
    {
        this.appointmentTime = appointmentTime;
    }

    public Date getAppointmentTime()
    {
        return appointmentTime;
    }
    public void setReportValidityTime(Date reportValidityTime)
    {
        this.reportValidityTime = reportValidityTime;
    }

    public Date getReportValidityTime()
    {
        return reportValidityTime;
    }
    public void setConsultantUserId(Integer consultantUserId)
    {
        this.consultantUserId = consultantUserId;
    }

    public Integer getConsultantUserId()
    {
        return consultantUserId;
    }
    public void setFirstVisitTime(Date firstVisitTime)
    {
        this.firstVisitTime = firstVisitTime;
    }

    public Long getChannelSysUserId() {
        return channelSysUserId;
    }

    public void setChannelSysUserId(Long channelSysUserId) {
        this.channelSysUserId = channelSysUserId;
    }

    public Long getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(Long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public Date getFirstVisitTime()
    {
        return firstVisitTime;
    }
    public void setVisitTimes(Integer visitTimes)
    {
        this.visitTimes = visitTimes;
    }

    public Integer getVisitTimes()
    {
        return visitTimes;
    }
    public void setCustomerRating(String customerRating)
    {
        this.customerRating = customerRating;
    }

    public String getCustomerRating()
    {
        return customerRating;
    }
    public void setPostponeStatus(String postponeStatus)
    {
        this.postponeStatus = postponeStatus;
    }

    public String getPostponeStatus()
    {
        return postponeStatus;
    }
    public void setReportStatus(String reportStatus)
    {
        this.reportStatus = reportStatus;
    }

    public String getReportStatus()
    {
        return reportStatus;
    }
    public void setValidStatus(String validStatus)
    {
        this.validStatus = validStatus;
    }

    public String getValidStatus()
    {
        return validStatus;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("reportNo", getReportNo())
            .append("userId", getUserId())
            .append("customerName", getCustomerName())
            .append("customerPhone", getCustomerPhone())
            .append("customerPhone", getCustomerBackupPhone())
            .append("customerPhone", getCustomerBackupPhone3())
            .append("intentionProjectId", getIntentionProjectId())
            .append("intentionHouses", getIntentionHouses())
            .append("purchasedProjectId", getPurchasedProjectId())
            .append("purchasedHousesId", getPurchasedHousesId())
            .append("middlemanUserId", getMiddlemanUserId())
            .append("reportTime", getReportTime())
            .append("appointmentTime", getAppointmentTime())
            .append("reportValidityTime", getReportValidityTime())
            .append("consultantUserId", getConsultantUserId())
            .append("firstVisitTime", getFirstVisitTime())
            .append("visitTimes", getVisitTimes())
            .append("customerRating", getCustomerRating())
            .append("postponeStatus", getPostponeStatus())
            .append("reportStatus", getReportStatus())
            .append("validStatus", getValidStatus())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
