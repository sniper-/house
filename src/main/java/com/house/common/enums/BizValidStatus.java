package com.house.common.enums;

public enum BizValidStatus {

    PROCESSING("0", "处理中"), INVALID("1", "无效"), EFFECTIVE("2", "有效");

    private final String code;
    private final String info;

    BizValidStatus(String code, String info)
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
