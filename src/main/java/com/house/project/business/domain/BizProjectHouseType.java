package com.house.project.business.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.house.framework.aspectj.lang.annotation.Excel;
import com.house.framework.web.domain.BaseEntity;

/**
 * 楼盘户型管理对象 biz_project_house_type
 * 
 */
public class BizProjectHouseType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增 */
    private Integer id;

    /** 楼盘编号 */
    @Excel(name = "楼盘编号")
    private String projectId;

    /** 户型名称 */
    @Excel(name = "户型名称")
    private String projectHouseName;

    /** 户型建筑面积(平米) */
    @Excel(name = "户型建筑面积(平米)")
    private BigDecimal floorArea;

    /** 最低总价(元) */
    @Excel(name = "最低总价(万元)")
    private BigDecimal minTotalPrice;

    /** 户型照片地址 */
    @Excel(name = "户型照片地址")
    private String projectHouseImageUrl;

    /** 顺序 */
    @Excel(name = "顺序")
    private Integer orderNum;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setProjectId(String projectId) 
    {
        this.projectId = projectId;
    }

    public String getProjectId() 
    {
        return projectId;
    }
    public void setProjectHouseName(String projectHouseName) 
    {
        this.projectHouseName = projectHouseName;
    }

    public String getProjectHouseName() 
    {
        return projectHouseName;
    }
    public void setFloorArea(BigDecimal floorArea) 
    {
        this.floorArea = floorArea;
    }

    public BigDecimal getFloorArea() 
    {
        return floorArea;
    }
    public void setMinTotalPrice(BigDecimal minTotalPrice) 
    {
        this.minTotalPrice = minTotalPrice;
    }

    public BigDecimal getMinTotalPrice() 
    {
        return minTotalPrice;
    }
    public void setProjectHouseImageUrl(String projectHouseImageUrl) 
    {
        this.projectHouseImageUrl = projectHouseImageUrl;
    }

    public String getProjectHouseImageUrl() 
    {
        return projectHouseImageUrl;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("projectHouseName", getProjectHouseName())
            .append("floorArea", getFloorArea())
            .append("minTotalPrice", getMinTotalPrice())
            .append("projectHouseImageUrl", getProjectHouseImageUrl())
            .append("order", getOrderNum())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
