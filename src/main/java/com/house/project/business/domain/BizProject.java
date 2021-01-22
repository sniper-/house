package com.house.project.business.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.house.framework.aspectj.lang.annotation.Excel;
import com.house.framework.web.domain.BaseEntity;

/**
 * 楼盘管理对象 biz_project
 * 
 */
public class BizProject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增(楼盘编号) */
    private Integer id;

    /** 楼盘名称 */
    @Excel(name = "楼盘名称")
    private String projectName;

    /** 开盘时间  YYYY-MM-DD */
    @Excel(name = "开盘时间  YYYY-MM-DD")
    private String openDate;

    /** 所在省份 */
    @Excel(name = "所在省份")
    private String projectProvince;

    /** 所在市 */
    @Excel(name = "所在市")
    private String projectCity;

    /** 详细地址 */
    @Excel(name = "详细地址")
    private String projectAddress;

    /** 楼盘状态（0-待售  1-在售  2-售罄 ） */
    @Excel(name = "楼盘状态", readConverterExp = "0=-待售,1=-在售,2=-售罄")
    private String projectStatus;

    /** 物业类型（住址、写字楼、别墅、商业、底商） */
    @Excel(name = "物业类型", readConverterExp = "住=址、写字楼、别墅、商业、底商")
    private String propertyType;

    /** 建筑类型（板楼、塔楼、板塔结合、独栋、叠拼、联排） */
    @Excel(name = "建筑类型", readConverterExp = "板=楼、塔楼、板塔结合、独栋、叠拼、联排")
    private String buildingTypes;

    /** 绿化率(%) */
    @Excel(name = "绿化率(%)")
    private BigDecimal greeningRate;

    /** 容积率 */
    @Excel(name = "容积率")
    private BigDecimal volumeRate;

    /** 占地面积  60~120平米 */
    @Excel(name = "占地面积  60~120平米")
    private String floorSpace;

    /** 建筑面积  60~120平米 */
    @Excel(name = "建筑面积  60~120平米")
    private String buildingSpace;

    /** 产权年限  40年 50年 70年 */
    @Excel(name = "产权年限  40年 50年 70年")
    private String propertyAgeLimit;

    /** 规划户数 */
    @Excel(name = "规划户数")
    private String programmeHouseholds;

    /** 楼盘均价(xxx元/平米、xxx万元/套) */
    @Excel(name = "楼盘均价(xxx元/平米、xxx万元/套)")
    private BigDecimal projectAveragePrice;

    /** 楼盘总价(xxx 万元/套起) */
    @Excel(name = "楼盘总价(xxx 万元/套起)")
    private String projectSumPrice;

    /** 不动产证书 */
    @Excel(name = "不动产证书")
    private String realEstateCertificate;

    /** 预售许可证 */
    @Excel(name = "预售许可证")
    private String preSaleLicense;

    /** 售楼处详细地址 */
    @Excel(name = "售楼处详细地址")
    private String salesOfficeAddress;

    /** 接待时间 例子 9:00 ~ 18:00 */
    @Excel(name = "接待时间 例子 9:00 ~ 18:00")
    private String receptionTime;

    /** 项目特色（复式、现房、大品牌、带花园、带飘窗、近地铁、小户型、车位充足、花园洋房、绿化率高、近医院、近学校枚举和自定义标签) */
    @Excel(name = "项目特色", readConverterExp = "项目特色（复式、现房、大品牌、带花园、带飘窗、近地铁、小户型、车位充足、花园洋房、绿化率高、近医院、近学校枚举和自定义标签)")
    private String projectFeatures;

    /** 供水（商水、民水) */
    @Excel(name = "供水", readConverterExp = "供水（商水、民水)")
    private String waterSupplyType;

    /** 天然气(有、无） */
    @Excel(name = "天然气(有、无）")
    private String naturalGas;

    /** 供热方式（集中供暖、地暖、电取暖） */
    @Excel(name = "供热方式", readConverterExp = "集=中供暖、地暖、电取暖")
    private String heatingType;

    /** 供电方式（商电、民电） */
    @Excel(name = "供电方式", readConverterExp = "商=电、民电")
    private String poweredType;

    /** 地铁站（x号线xx站，x号线xx站） */
    @Excel(name = "地铁站", readConverterExp = "x=号线xx站，x号线xx站")
    private String subwayStation;

    /** 车位（地上、地下、无） */
    @Excel(name = "车位", readConverterExp = "地=上、地下、无")
    private String parkingSpace;

    /** 车位配比 （1：x） */
    @Excel(name = "车位配比 ", readConverterExp = "1=：x")
    private String parkingSpaceScale;

    /** 物业费（x元/月、x元/平米/月） */
    @Excel(name = "物业费", readConverterExp = "x=元/月、x元/平米/月")
    private String propertyCosts;

    /** 开发商 */
    @Excel(name = "开发商")
    private String developer;

    /** 物业公司 */
    @Excel(name = "物业公司")
    private String propertyCompany;

    /** 配套设施（地铁、教育设施、医院、购物、公园、其他） */
    @Excel(name = "配套设施", readConverterExp = "地=铁、教育设施、医院、购物、公园、其他")
    private String matching;

    /** 楼盘动态 */
    @Excel(name = "楼盘动态")
    private String propertyNews;

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
    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }
    public void setOpenDate(String openDate) 
    {
        this.openDate = openDate;
    }

    public String getOpenDate() 
    {
        return openDate;
    }
    public void setProjectProvince(String projectProvince) 
    {
        this.projectProvince = projectProvince;
    }

    public String getProjectProvince() 
    {
        return projectProvince;
    }
    public void setProjectCity(String projectCity) 
    {
        this.projectCity = projectCity;
    }

    public String getProjectCity() 
    {
        return projectCity;
    }
    public void setProjectAddress(String projectAddress) 
    {
        this.projectAddress = projectAddress;
    }

    public String getProjectAddress() 
    {
        return projectAddress;
    }
    public void setProjectStatus(String projectStatus) 
    {
        this.projectStatus = projectStatus;
    }

    public String getProjectStatus() 
    {
        return projectStatus;
    }
    public void setPropertyType(String propertyType) 
    {
        this.propertyType = propertyType;
    }

    public String getPropertyType() 
    {
        return propertyType;
    }
    public void setBuildingTypes(String buildingTypes) 
    {
        this.buildingTypes = buildingTypes;
    }

    public String getBuildingTypes() 
    {
        return buildingTypes;
    }
    public void setGreeningRate(BigDecimal greeningRate) 
    {
        this.greeningRate = greeningRate;
    }

    public BigDecimal getGreeningRate() 
    {
        return greeningRate;
    }
    public void setVolumeRate(BigDecimal volumeRate) 
    {
        this.volumeRate = volumeRate;
    }

    public BigDecimal getVolumeRate() 
    {
        return volumeRate;
    }
    public void setFloorSpace(String floorSpace) 
    {
        this.floorSpace = floorSpace;
    }

    public String getFloorSpace() 
    {
        return floorSpace;
    }
    public void setBuildingSpace(String buildingSpace) 
    {
        this.buildingSpace = buildingSpace;
    }

    public String getBuildingSpace() 
    {
        return buildingSpace;
    }
    public void setPropertyAgeLimit(String propertyAgeLimit) 
    {
        this.propertyAgeLimit = propertyAgeLimit;
    }

    public String getPropertyAgeLimit() 
    {
        return propertyAgeLimit;
    }
    public void setProgrammeHouseholds(String programmeHouseholds) 
    {
        this.programmeHouseholds = programmeHouseholds;
    }

    public String getProgrammeHouseholds() 
    {
        return programmeHouseholds;
    }
    public void setProjectAveragePrice(BigDecimal projectAveragePrice) 
    {
        this.projectAveragePrice = projectAveragePrice;
    }

    public BigDecimal getProjectAveragePrice() 
    {
        return projectAveragePrice;
    }
    public void setProjectSumPrice(String projectSumPrice) 
    {
        this.projectSumPrice = projectSumPrice;
    }

    public String getProjectSumPrice() 
    {
        return projectSumPrice;
    }
    public void setRealEstateCertificate(String realEstateCertificate) 
    {
        this.realEstateCertificate = realEstateCertificate;
    }

    public String getRealEstateCertificate() 
    {
        return realEstateCertificate;
    }
    public void setPreSaleLicense(String preSaleLicense) 
    {
        this.preSaleLicense = preSaleLicense;
    }

    public String getPreSaleLicense() 
    {
        return preSaleLicense;
    }
    public void setSalesOfficeAddress(String salesOfficeAddress) 
    {
        this.salesOfficeAddress = salesOfficeAddress;
    }

    public String getSalesOfficeAddress() 
    {
        return salesOfficeAddress;
    }
    public void setReceptionTime(String receptionTime) 
    {
        this.receptionTime = receptionTime;
    }

    public String getReceptionTime() 
    {
        return receptionTime;
    }
    public void setProjectFeatures(String projectFeatures) 
    {
        this.projectFeatures = projectFeatures;
    }

    public String getProjectFeatures() 
    {
        return projectFeatures;
    }
    public void setWaterSupplyType(String waterSupplyType) 
    {
        this.waterSupplyType = waterSupplyType;
    }

    public String getWaterSupplyType() 
    {
        return waterSupplyType;
    }
    public void setNaturalGas(String naturalGas) 
    {
        this.naturalGas = naturalGas;
    }

    public String getNaturalGas() 
    {
        return naturalGas;
    }
    public void setHeatingType(String heatingType) 
    {
        this.heatingType = heatingType;
    }

    public String getHeatingType() 
    {
        return heatingType;
    }
    public void setPoweredType(String poweredType)
    {
        this.poweredType = poweredType;
    }

    public String getPoweredType()
    {
        return poweredType;
    }
    public void setSubwayStation(String subwayStation) 
    {
        this.subwayStation = subwayStation;
    }

    public String getSubwayStation() 
    {
        return subwayStation;
    }
    public void setParkingSpace(String parkingSpace) 
    {
        this.parkingSpace = parkingSpace;
    }

    public String getParkingSpace() 
    {
        return parkingSpace;
    }
    public void setParkingSpaceScale(String parkingSpaceScale) 
    {
        this.parkingSpaceScale = parkingSpaceScale;
    }

    public String getParkingSpaceScale() 
    {
        return parkingSpaceScale;
    }
    public void setPropertyCosts(String propertyCosts) 
    {
        this.propertyCosts = propertyCosts;
    }

    public String getPropertyCosts() 
    {
        return propertyCosts;
    }
    public void setDeveloper(String developer) 
    {
        this.developer = developer;
    }

    public String getDeveloper() 
    {
        return developer;
    }
    public void setPropertyCompany(String propertyCompany) 
    {
        this.propertyCompany = propertyCompany;
    }

    public String getPropertyCompany() 
    {
        return propertyCompany;
    }
    public void setMatching(String matching) 
    {
        this.matching = matching;
    }

    public String getMatching() 
    {
        return matching;
    }
    public void setPropertyNews(String propertyNews) 
    {
        this.propertyNews = propertyNews;
    }

    public String getPropertyNews() 
    {
        return propertyNews;
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
            .append("projectName", getProjectName())
            .append("openDate", getOpenDate())
            .append("projectProvince", getProjectProvince())
            .append("projectCity", getProjectCity())
            .append("projectAddress", getProjectAddress())
            .append("projectStatus", getProjectStatus())
            .append("propertyType", getPropertyType())
            .append("buildingTypes", getBuildingTypes())
            .append("greeningRate", getGreeningRate())
            .append("volumeRate", getVolumeRate())
            .append("floorSpace", getFloorSpace())
            .append("buildingSpace", getBuildingSpace())
            .append("propertyAgeLimit", getPropertyAgeLimit())
            .append("programmeHouseholds", getProgrammeHouseholds())
            .append("projectAveragePrice", getProjectAveragePrice())
            .append("projectSumPrice", getProjectSumPrice())
            .append("realEstateCertificate", getRealEstateCertificate())
            .append("preSaleLicense", getPreSaleLicense())
            .append("salesOfficeAddress", getSalesOfficeAddress())
            .append("receptionTime", getReceptionTime())
            .append("projectFeatures", getProjectFeatures())
            .append("waterSupplyType", getWaterSupplyType())
            .append("naturalGas", getNaturalGas())
            .append("heatingType", getHeatingType())
            .append("powered Type", getPoweredType())
            .append("subwayStation", getSubwayStation())
            .append("parkingSpace", getParkingSpace())
            .append("parkingSpaceScale", getParkingSpaceScale())
            .append("propertyCosts", getPropertyCosts())
            .append("developer", getDeveloper())
            .append("propertyCompany", getPropertyCompany())
            .append("matching", getMatching())
            .append("propertyNews", getPropertyNews())
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
