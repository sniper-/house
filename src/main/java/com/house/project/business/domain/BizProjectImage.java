package com.house.project.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.house.framework.aspectj.lang.annotation.Excel;
import com.house.framework.web.domain.BaseEntity;

/**
 * 楼盘照片管理对象 biz_project_image
 * 
 */
public class BizProjectImage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增 */
    private Integer id;

    /** 楼盘编号 */
    @Excel(name = "楼盘编号")
    private String projectId;

    /** 楼盘照片类型（户型图、效果图、实景图、小区配套、样板间、其他） */
    @Excel(name = "楼盘照片类型", readConverterExp = "1-户型图、2-效果图、3-实景图、4-小区配套、5-样板间、6-其他")
    private String imageType;

    /** 楼盘照片地址 */
    @Excel(name = "楼盘照片地址")
    private String projectImageUrl;

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
    public void setImageType(String imageType) 
    {
        this.imageType = imageType;
    }

    public String getImageType() 
    {
        return imageType;
    }
    public void setProjectImageUrl(String projectImageUrl) 
    {
        this.projectImageUrl = projectImageUrl;
    }

    public String getProjectImageUrl() 
    {
        return projectImageUrl;
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
            .append("imageType", getImageType())
            .append("projectImageUrl", getProjectImageUrl())
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
