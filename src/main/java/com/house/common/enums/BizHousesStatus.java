package com.house.common.enums;

public enum BizHousesStatus {

    ON_SALE("1", "在售"), SOLD("2", "已售"), IS_SALE("3", "售中"),
    DELIVER("4", "交付");

    private final String code;
    private final String info;

    BizHousesStatus(String code, String info)
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
