package com.house.project.business.domain.vo;

import com.house.project.business.domain.BizProjectHouseType;

import java.math.BigDecimal;

public class BizProjectHouseTypeVo extends BizProjectHouseType {

    /** 户型建筑面积下限(平米) */
    private BigDecimal floorAreaLow;

    /** 户型建筑面积上限(平米) */
    private BigDecimal floorAreaHigh;

    /** 最低总价下限(元) */
    private BigDecimal minTotalPriceLow;

    /** 最低总价高上限(平米) */
    private BigDecimal minTotalPriceHigh;

    public BigDecimal getFloorAreaLow() {
        return floorAreaLow;
    }

    public void setFloorAreaLow(BigDecimal floorAreaLow) {
        this.floorAreaLow = floorAreaLow;
    }

    public BigDecimal getFloorAreaHigh() {
        return floorAreaHigh;
    }

    public void setFloorAreaHigh(BigDecimal floorAreaHigh) {
        this.floorAreaHigh = floorAreaHigh;
    }

    public BigDecimal getMinTotalPriceLow() {
        return minTotalPriceLow;
    }

    public void setMinTotalPriceLow(BigDecimal minTotalPriceLow) {
        this.minTotalPriceLow = minTotalPriceLow;
    }

    public BigDecimal getMinTotalPriceHigh() {
        return minTotalPriceHigh;
    }

    public void setMinTotalPriceHigh(BigDecimal minTotalPriceHigh) {
        this.minTotalPriceHigh = minTotalPriceHigh;
    }
}
