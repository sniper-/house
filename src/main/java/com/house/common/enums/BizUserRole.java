package com.house.common.enums;

/**
 * 业务用户角色
 *
 * @author bo.zhang
 */
public enum BizUserRole {

    AGENT("1", "经纪人"),
    OWNER("2", "店长"),
    MEDIUM_MANAGER("3", "中介公司经理"),
    RECEPTION("4", "前台"),
    ADVISER("5", "置业顾问"),
    COMPANY_MANAGER("6", "房产公司经理");

    private final String code;
    private final String info;

    BizUserRole(String code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public String getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
