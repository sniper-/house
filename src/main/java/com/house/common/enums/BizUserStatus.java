package com.house.common.enums;

public enum BizUserStatus {

    QUIT("1", "离职"),
    JOB("0", "在职");


    private final String code;
    private final String info;

    BizUserStatus(String code, String info)
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
