package com.house.project.business.domain.vo;

public class BizQueryOrgVo {

    /** 用户角色:1-经纪人 2-店长 3-中介公司经理 4-前台 5-置业顾问 6-房产公司经理 */
    private String userRole;

    /** 机构编码 */
    private String organizationCode;

    /** 机构名称 */
    private String organizationName;

    public BizQueryOrgVo() {
    }

    public BizQueryOrgVo(String organizationCode, String organizationName) {
        this.organizationCode = organizationCode;
        this.organizationName = organizationName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
