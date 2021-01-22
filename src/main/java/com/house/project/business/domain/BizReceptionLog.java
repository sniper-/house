package com.house.project.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.house.framework.aspectj.lang.annotation.Excel;
import com.house.framework.web.domain.BaseEntity;

/**
 * 接待日志对象 biz_reception_log
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
public class BizReceptionLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增(报备编号) */
    private Integer id;

    /** 报备编号 */
    @Excel(name = "报备编号")
    private String reportNo;

    /** 置业顾问编号 */
    @Excel(name = "置业顾问编号")
    private Integer consultantUserId;

    /** 接待日志 */
    @Excel(name = "接待日志")
    private String receptionLog;

    /** 接待时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "接待时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date receptionTime;

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

    public void setReportNo(String reportNo)
    {
        this.reportNo = reportNo;
    }

    public String getReportNo() 
    {
        return reportNo;
    }
    public void setConsultantUserId(Integer consultantUserId) 
    {
        this.consultantUserId = consultantUserId;
    }

    public Integer getConsultantUserId() 
    {
        return consultantUserId;
    }
    public void setReceptionLog(String receptionLog) 
    {
        this.receptionLog = receptionLog;
    }

    public String getReceptionLog() 
    {
        return receptionLog;
    }
    public void setReceptionTime(Date receptionTime) 
    {
        this.receptionTime = receptionTime;
    }

    public Date getReceptionTime() 
    {
        return receptionTime;
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
            .append("consultantUserId", getConsultantUserId())
            .append("receptionLog", getReceptionLog())
            .append("receptionTime", getReceptionTime())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
