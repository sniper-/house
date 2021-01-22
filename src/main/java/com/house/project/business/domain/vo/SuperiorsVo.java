package com.house.project.business.domain.vo;

public class SuperiorsVo {

    /** 用户角色:1-经纪人 2-店长 3-中介公司经理 4-前台 5-置业顾问 6-房产公司经理  */
    private String userRole;

    /** 所属机构编号 */
    private String organizationCode;

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
}
