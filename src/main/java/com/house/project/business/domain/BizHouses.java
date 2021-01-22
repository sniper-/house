package com.house.project.business.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.house.common.utils.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.house.framework.aspectj.lang.annotation.Excel;
import com.house.framework.web.domain.BaseEntity;

/**
 * 房源管理对象 biz_houses
 * 
 */
public class BizHouses extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增(房源编号) */
    private Integer id;

    /** 楼盘编号 */
    @Excel(name = "楼盘编号", type = Excel.Type.IMPORT)
    private Integer projectId;

    /** 楼盘户型编号 */
    @Excel(name = "楼盘户型编号", type = Excel.Type.IMPORT)
    private Integer projectHouseTypeId;

    /** 户型面积(平米) */
    @Excel(name = "户型面积(平米)")
    private BigDecimal houseArea;

    /** 楼号 */
    @Excel(name = "楼号")
    private String floorNo;

    /** 单元号 */
    @Excel(name = "单元号")
    private String unitNo;

    /** 楼层 */
    @Excel(name = "楼层")
    private String storeyNo;

    /** 门牌号 */
    @Excel(name = "门牌号")
    private String houseNo;

    /** 特色（厕所有窗、靠近防火通道、入户花园、赠送阳台） */
    @Excel(name = "特色")
    private String features;

    /** 房源状态  （1.在售、2.已售、3.售中、4.交付） */
    @Excel(name = "房源状态  ", readConverterExp = "1=在售,2=已售,3=售中,4=交付")
    private String housesStatus;

    /** 原价（x元/平米，平方数，价格） */
    @Excel(name = "原价")
    private BigDecimal originalPrice;

    /** 调整价格(元 正数为调增 负数调减) */
    @Excel(name = "调整价格(元 正数为调增 负数调减)")
    private BigDecimal adjustPrice;

    /** 折扣 */
    @Excel(name = "折扣")
    private String discount;

    /** 成交价(元） */
    @Excel(name = "成交价(元）")
    private BigDecimal dealPrice;

    /** 成交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dealDate;

    /** 优惠说明 */
    @Excel(name = "优惠说明")
    private String offerDescription;

    /** 状态（0正常 1停用） */
    private String status;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setProjectId(Integer projectId) 
    {
        this.projectId = projectId;
    }

    public Integer getProjectId() 
    {
        return projectId;
    }

    public Integer getProjectHouseTypeId() {
        return projectHouseTypeId;
    }

    public void setProjectHouseTypeId(Integer projectHouseTypeId) {
        this.projectHouseTypeId = projectHouseTypeId;
    }

    public void setHouseArea(BigDecimal houseArea)
    {
        this.houseArea = houseArea;
    }

    public BigDecimal getHouseArea() 
    {
        return houseArea;
    }
    public void setFloorNo(String floorNo) 
    {
        this.floorNo = floorNo;
    }

    public String getFloorNo() 
    {
        return floorNo;
    }
    public void setUnitNo(String unitNo) 
    {
        this.unitNo = unitNo;
    }

    public String getUnitNo() 
    {
        return unitNo;
    }
    public void setStoreyNo(String storeyNo) 
    {
        this.storeyNo = storeyNo;
    }

    public String getStoreyNo() 
    {
        return storeyNo;
    }
    public void setHouseNo(String houseNo) 
    {
        this.houseNo = houseNo;
    }

    public String getHouseNo() 
    {
        return houseNo;
    }
    public void setFeatures(String features) 
    {
        this.features = features;
    }

    public String getFeatures() 
    {
        return features;
    }
    public void setHousesStatus(String housesStatus) 
    {
        this.housesStatus = housesStatus;
    }

    public String getHousesStatus() 
    {
        return housesStatus;
    }
    public void setOriginalPrice(BigDecimal originalPrice) 
    {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getOriginalPrice() 
    {
        return originalPrice;
    }
    public void setAdjustPrice(BigDecimal adjustPrice) 
    {
        this.adjustPrice = adjustPrice;
    }

    public BigDecimal getAdjustPrice() 
    {
        return adjustPrice;
    }
    public void setDiscount(String discount) 
    {
        this.discount = discount;
    }

    public String getDiscount() 
    {
        return discount;
    }
    public void setDealPrice(BigDecimal dealPrice) 
    {
        this.dealPrice = dealPrice;
    }

    public BigDecimal getDealPrice() 
    {
        return dealPrice;
    }
    public void setOfferDescription(String offerDescription) 
    {
        this.offerDescription = offerDescription;
    }

    public Date getDealDate() {
        return dealDate;
    }

    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    public String getOfferDescription()
    {
        return offerDescription;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    /**
     * 房源名称
     * @return 结果
     */
    public String buildHouseName() {
        String floorNo = StringUtils.isEmpty(getFloorNo()) ? "" : getFloorNo();
        String unitNo = StringUtils.isEmpty(getUnitNo()) ? "" : getUnitNo();
        String storeyNo = StringUtils.isEmpty(getStoreyNo()) ? "" : getStoreyNo();
        String houseNo = StringUtils.isEmpty(getHouseNo()) ? "" : getHouseNo();
        return floorNo.concat(unitNo).concat(storeyNo).concat(houseNo);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("projectHouseType", getProjectHouseTypeId())
            .append("houseArea", getHouseArea())
            .append("floorNo", getFloorNo())
            .append("unitNo", getUnitNo())
            .append("storeyNo", getStoreyNo())
            .append("houseNo", getHouseNo())
            .append("features", getFeatures())
            .append("housesStatus", getHousesStatus())
            .append("originalPrice", getOriginalPrice())
            .append("adjustPrice", getAdjustPrice())
            .append("discount", getDiscount())
            .append("dealPrice", getDealPrice())
            .append("dealDate", getDealDate())
            .append("offerDescription", getOfferDescription())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
