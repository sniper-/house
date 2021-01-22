package com.house.common.enums;

public enum BizImageType {

    HX("1", "户型图"),
    XG("2", "效果图"),
    SJ("3", "实景图"),
    XQ("4", "小区配套"),
    YB("5", "样板间"),
    QT("6", "其他");

    private final String code;
    private final String info;

    BizImageType(String code, String info)
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
