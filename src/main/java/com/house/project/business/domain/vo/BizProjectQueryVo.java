package com.house.project.business.domain.vo;

import com.house.project.business.domain.BizProject;
import com.house.project.business.domain.BizProjectImage;

import java.util.List;


public class BizProjectQueryVo extends BizProject {

    /**
     * 楼盘效果图第一张照片
     */
    private String imageUrl;

    /**
     * 楼盘照片列表
     */
    private List<BizProjectImage> projectImages;


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<BizProjectImage> getProjectImages() {
        return projectImages;
    }

    public void setProjectImages(List<BizProjectImage> projectImages) {
        this.projectImages = projectImages;
    }
}
