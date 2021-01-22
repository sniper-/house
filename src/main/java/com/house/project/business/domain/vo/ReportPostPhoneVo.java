package com.house.project.business.domain.vo;

import com.house.project.business.domain.BizDimissionDistribution;

import java.util.Date;
import java.util.List;

/**
 * 报备信息延期审核
 */
public class ReportPostPhoneVo {

    /**
     * 报备id
     */
    private Integer id;

    /**
     * 报备编号
     */
    private String reportNo;

    /**
     * 新报备结束时间
     */
    private Date reportValidTime;

    /**
     * 增加报备天数
     */
    private Integer addDays;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReportNo() {
        return reportNo;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

    public Date getReportValidTime() {
        return reportValidTime;
    }

    public void setReportValidTime(Date reportValidTime) {
        this.reportValidTime = reportValidTime;
    }

    public Integer getAddDays() {
        return addDays;
    }

    public void setAddDays(Integer addDays) {
        this.addDays = addDays;
    }
}
