package com.house.common.enums;

public enum BizCheckStatus {

    NOT_APPROVE("0", "待审核"),
    APPROVE_SUCCESS("1", "审核成功"),
    APPROVE_FAIL("2", "审核失败");

    private final String code;
    private final String info;

    BizCheckStatus(String code, String info)
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
