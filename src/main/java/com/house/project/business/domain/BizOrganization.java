package com.house.project.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.house.framework.aspectj.lang.annotation.Excel;
import com.house.framework.web.domain.BaseEntity;

/**
 * 机构管理对象 biz_organization
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
public class BizOrganization extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增 */
    private Integer id;

    /** 机构编码 */
    private String organizationCode;

    /** 机构类型(1-中介机构 2-房产公司) */
    @Excel(name = "机构类型", readConverterExp = "1=中介机构,2=房产公司")
    private String organizationType;

    /** 机构名称 */
    @Excel(name = "机构名称")
    private String organizationName;

    /** 机构地址 */
    @Excel(name = "机构地址")
    private String organizationAddress;

    /** 机构电话 */
    @Excel(name = "机构电话")
    private String organizationPhone;

    /** 机构地址经度 */
    @Excel(name = "机构地址经度", type = Excel.Type.EXPORT)
    private String organizationAddressLongitude;

    /** 机构地址纬度 */
    @Excel(name = "机构地址纬度", type = Excel.Type.EXPORT)
    private String organizationAddressLatitude;

    /** 机构状态（0正常 1停用） */
    @Excel(name = "机构状态", readConverterExp = "0=正常,1=停用")
    private String organizationStatus;

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

    public void setOrganizationCode(String organizationCode)
    {
        this.organizationCode = organizationCode;
    }

    public String getOrganizationCode() 
    {
        return organizationCode;
    }
    public void setOrganizationType(String organizationType) 
    {
        this.organizationType = organizationType;
    }

    public String getOrganizationType() 
    {
        return organizationType;
    }

    public void setOrganizationName(String organizationName) 
    {
        this.organizationName = organizationName;
    }

    public String getOrganizationName()
    {
        return organizationName;
    }
    public void setOrganizationAddress(String organizationAddress) 
    {
        this.organizationAddress = organizationAddress;
    }

    public String getOrganizationAddress() 
    {
        return organizationAddress;
    }
    public void setOrganizationPhone(String organizationPhone) 
    {
        this.organizationPhone = organizationPhone;
    }

    public String getOrganizationPhone() 
    {
        return organizationPhone;
    }

    public void setOrganizationAddressLongitude(String organizationAddressLongitude)
    {
        this.organizationAddressLongitude = organizationAddressLongitude;
    }

    public String getOrganizationAddressLongitude() 
    {
        return organizationAddressLongitude;
    }
    public void setOrganizationAddressLatitude(String organizationAddressLatitude) 
    {
        this.organizationAddressLatitude = organizationAddressLatitude;
    }

    public String getOrganizationAddressLatitude() 
    {
        return organizationAddressLatitude;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getOrganizationStatus() {
        return organizationStatus;
    }

    public void setOrganizationStatus(String organizationStatus) {
        this.organizationStatus = organizationStatus;
    }

    public String getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("organizationCode", getOrganizationCode())
            .append("organizationType", getOrganizationType())
            .append("organizationName", getOrganizationName())
            .append("organizationAddress", getOrganizationAddress())
            .append("organizationPhone", getOrganizationPhone())
            .append("organizationAddressLongitude", getOrganizationAddressLongitude())
            .append("organizationAddressLatitude", getOrganizationAddressLatitude())
            .append("organizationStatus", getOrganizationStatus())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
