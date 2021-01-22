package com.house.common.enums;

/**
 * 延期申请状态
 */
public enum BizPostponeStatus {

    NOT_REVIEWED("0", "未申请"),
    BE_REVIEWED("1", "待审核"),
    REVIEWED("2", "已审核");

    private final String code;
    private final String info;

    BizPostponeStatus(String code, String info)
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
