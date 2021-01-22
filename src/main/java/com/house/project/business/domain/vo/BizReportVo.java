package com.house.project.business.domain.vo;

import com.house.framework.aspectj.lang.annotation.Excel;
import com.house.project.business.domain.BizReport;


public class BizReportVo extends BizReport {

    /** 成交楼盘编号 */
    @Excel(name = "意向楼盘", type = Excel.Type.EXPORT)
    private String intentionProject;

    /** 服务商名称 */
    @Excel(name = "服务商名称", type = Excel.Type.EXPORT)
    private String deptName;

    /** 经纪人编号 */
    @Excel(name = "经纪人名称", type = Excel.Type.EXPORT)
    private String middlemanName;

    /** 经纪人公司 */
    @Excel(name = "经纪人公司", type = Excel.Type.EXPORT)
    private String middlemanCompany;

    /** 经纪人电话 */
    @Excel(name = "经纪人电话", type = Excel.Type.EXPORT)
    private String middlemanPhone;

    /** 置业顾问名称 */
    @Excel(name = "置业顾问名称", type = Excel.Type.EXPORT)
    private String consultantName;

    /** 成交楼盘 */
    @Excel(name = "成交楼盘", type = Excel.Type.EXPORT)
    private String purchasedProject;

    /** 渠道专员编号 */
    @Excel(name = "渠道专员", type = Excel.Type.EXPORT)
    private String channelSysUser;


    /** 成交房源 */
    @Excel(name = "成交房源", type = Excel.Type.EXPORT)
    private String purchasedHouses;

    /** 楼号 */
    private String floorNo;

    /** 单元号 */
    private String unitNo;

    /** 楼层 */
    private String storeyNo;

    /** 门牌号 */
    private String houseNo;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getMiddlemanName() {
        return middlemanName;
    }

    public void setMiddlemanName(String middlemanName) {
        this.middlemanName = middlemanName;
    }

    public String getConsultantName() {
        return consultantName;
    }

    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }

    public String getMiddlemanCompany() {
        return middlemanCompany;
    }

    public void setMiddlemanCompany(String middlemanCompany) {
        this.middlemanCompany = middlemanCompany;
    }

    public String getMiddlemanPhone() {
        return middlemanPhone;
    }

    public void setMiddlemanPhone(String middlemanPhone) {
        this.middlemanPhone = middlemanPhone;
    }

    public String getIntentionProject() {
        return intentionProject;
    }

    public void setIntentionProject(String intentionProject) {
        this.intentionProject = intentionProject;
    }

    public String getPurchasedProject() {
        return purchasedProject;
    }

    public void setPurchasedHouses(String purchasedHouses) {
        this.purchasedHouses = purchasedHouses;
    }

    public String getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    public String getStoreyNo() {
        return storeyNo;
    }

    public void setStoreyNo(String storeyNo) {
        this.storeyNo = storeyNo;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getChannelSysUser() {
        return channelSysUser;
    }

    public void setChannelSysUser(String channelSysUser) {
        this.channelSysUser = channelSysUser;
    }

    public void setPurchasedProject(String purchasedProject) {
        this.purchasedProject = purchasedProject;
    }

    public String getPurchasedHouses() {
        return (floorNo == null ? "" : floorNo).
                concat(unitNo == null ? "" : unitNo).
                concat(storeyNo == null ? "" : storeyNo).
                concat(houseNo == null ? "" : houseNo);
    }
}
