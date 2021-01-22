package com.house.project.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.house.framework.aspectj.lang.annotation.Excel;
import com.house.framework.web.domain.BaseEntity;

/**
 * 离职分配对象 biz_dimission_distribution
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
public class BizDimissionDistribution extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增 */
    private Integer id;

    /** 分配批次 */
    @Excel(name = "分配批次")
    private String distributionBatch;

    /** 报备编号 */
    @Excel(name = "报备编号")
    private String reportNo;

    /** 原始顾问编号 */
    @Excel(name = "原始顾问编号")
    private Integer originalRconsultantUserId;

    /** 新顾问编号 */
    @Excel(name = "新顾问编号")
    private Integer newConsultantUserId;

    /** 分配时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "分配时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date distributionTime;

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
    public void setDistributionBatch(String distributionBatch) 
    {
        this.distributionBatch = distributionBatch;
    }

    public String getDistributionBatch() 
    {
        return distributionBatch;
    }
    public void setReportNo(String reportNo) 
    {
        this.reportNo = reportNo;
    }

    public String getReportNo() 
    {
        return reportNo;
    }
    public void setOriginalRconsultantUserId(Integer originalRconsultantUserId) 
    {
        this.originalRconsultantUserId = originalRconsultantUserId;
    }

    public Integer getOriginalRconsultantUserId() 
    {
        return originalRconsultantUserId;
    }
    public void setNewConsultantUserId(Integer newConsultantUserId) 
    {
        this.newConsultantUserId = newConsultantUserId;
    }

    public Integer getNewConsultantUserId() 
    {
        return newConsultantUserId;
    }
    public void setDistributionTime(Date distributionTime) 
    {
        this.distributionTime = distributionTime;
    }

    public Date getDistributionTime() 
    {
        return distributionTime;
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
            .append("distributionBatch", getDistributionBatch())
            .append("reportNo", getReportNo())
            .append("originalRconsultantUserId", getOriginalRconsultantUserId())
            .append("newConsultantUserId", getNewConsultantUserId())
            .append("distributionTime", getDistributionTime())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
