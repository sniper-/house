package com.house.common.enums;

public enum BizReportStatus {

    NO_VISIT("0", "未到访"), VISIT("1", "已到访"), SF_PAID("2", "已交认筹金"),
    INTEREST_PAID("3", "已交意向金"), DEPOSIT_PAID("4", "已交定金"),
    DOWN_PAID("5", "首付已交齐"), ONLINE_SIGNED("6", "已网签"),
    PROCESSED_LOANS("7", "已办理贷款"), FULL_PAID("8", "已全款"),
    DELIVERED("9", "已交房"),HOUSES_PROPERTY("10", "已办理房产证");

    private final String code;
    private final String info;

    BizReportStatus(String code, String info)
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
