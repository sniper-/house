package com.house.project.business.domain.vo;


import java.math.BigDecimal;
import java.util.List;

public class BizCollectVo {

    /** 报备数 */
    private Integer reportNum;
    /** 月到访数 */
    private Integer visitNum;
    /** 月成交数 */
    private Integer dealNum;
    /** 月成交金额 */
    private BigDecimal monthSettledAmount;

    /** 1-12月份的报备数 */
    private List<Integer> reportNumList;
    /** 1-12月份的到访数 */
    private List<Integer> visitNumList;
    /** 1-12月份的成交数 */
    private List<Integer> dealNumList;
    /** 1-12月份的成交金额集合 */
    private List<BigDecimal> monthSettledList;

    public Integer getReportNum() {
        return reportNum;
    }

    public void setReportNum(Integer reportNum) {
        this.reportNum = reportNum;
    }

    public Integer getVisitNum() {
        return visitNum;
    }

    public void setVisitNum(Integer visitNum) {
        this.visitNum = visitNum;
    }

    public Integer getDealNum() {
        return dealNum;
    }

    public void setDealNum(Integer dealNum) {
        this.dealNum = dealNum;
    }

    public BigDecimal getMonthSettledAmount() {
        return monthSettledAmount;
    }

    public void setMonthSettledAmount(BigDecimal monthSettledAmount) {
        this.monthSettledAmount = monthSettledAmount;
    }

    public List<Integer> getReportNumList() {
        return reportNumList;
    }

    public void setReportNumList(List<Integer> reportNumList) {
        this.reportNumList = reportNumList;
    }

    public List<Integer> getVisitNumList() {
        return visitNumList;
    }

    public void setVisitNumList(List<Integer> visitNumList) {
        this.visitNumList = visitNumList;
    }

    public List<Integer> getDealNumList() {
        return dealNumList;
    }

    public void setDealNumList(List<Integer> dealNumList) {
        this.dealNumList = dealNumList;
    }

    public List<BigDecimal> getMonthSettledList() {
        return monthSettledList;
    }

    public void setMonthSettledList(List<BigDecimal> monthSettledList) {
        this.monthSettledList = monthSettledList;
    }
}
