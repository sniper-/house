package com.house.common.enums;

public enum BizOrgType {

    INTERMEDIARY("1", "中介公司"),
    HOUSING("2", "房产公司");


    private final String code;
    private final String info;

    BizOrgType(String code, String info)
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
