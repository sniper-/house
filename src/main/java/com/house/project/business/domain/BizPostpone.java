package com.house.project.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.house.framework.aspectj.lang.annotation.Excel;
import com.house.framework.web.domain.BaseEntity;

/**
 * 延期申请对象 biz_postpone
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
public class BizPostpone extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增 */
    private Integer id;

    /** 延期申请编号 */
    @Excel(name = "延期申请编号")
    private String postponeNo;

    /** 报备编号 */
    @Excel(name = "报备编号")
    private String reportNo;

    /** 申请时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applicationTime;

    /** 审核状态  0-待审核  1-审核成功  2-审核失败 */
    @Excel(name = "审核状态  0-待审核  1-审核成功  2-审核失败")
    private String checkStatus;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkTime;

    /** 增加报备天数 */
    @Excel(name = "增加报备天数")
    private Integer addDays;

    /** 原始报备结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "原始报备结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date originalReportTime;

    /** 新报备结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "新报备结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date newReportTime;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setPostponeNo(String postponeNo) 
    {
        this.postponeNo = postponeNo;
    }

    public String getPostponeNo() 
    {
        return postponeNo;
    }
    public void setReportNo(String reportNo) 
    {
        this.reportNo = reportNo;
    }

    public String getReportNo() 
    {
        return reportNo;
    }
    public void setApplicationTime(Date applicationTime) 
    {
        this.applicationTime = applicationTime;
    }

    public Date getApplicationTime() 
    {
        return applicationTime;
    }
    public void setCheckStatus(String checkStatus) 
    {
        this.checkStatus = checkStatus;
    }

    public String getCheckStatus() 
    {
        return checkStatus;
    }
    public void setCheckTime(Date checkTime) 
    {
        this.checkTime = checkTime;
    }

    public Date getCheckTime() 
    {
        return checkTime;
    }
    public void setAddDays(Integer addDays) 
    {
        this.addDays = addDays;
    }

    public Integer getAddDays() 
    {
        return addDays;
    }
    public void setOriginalReportTime(Date originalReportTime) 
    {
        this.originalReportTime = originalReportTime;
    }

    public Date getOriginalReportTime() 
    {
        return originalReportTime;
    }
    public void setNewReportTime(Date newReportTime) 
    {
        this.newReportTime = newReportTime;
    }

    public Date getNewReportTime() 
    {
        return newReportTime;
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
            .append("postponeNo", getPostponeNo())
            .append("reportNo", getReportNo())
            .append("applicationTime", getApplicationTime())
            .append("checkStatus", getCheckStatus())
            .append("checkTime", getCheckTime())
            .append("addDays", getAddDays())
            .append("originalReportTime", getOriginalReportTime())
            .append("newReportTime", getNewReportTime())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
