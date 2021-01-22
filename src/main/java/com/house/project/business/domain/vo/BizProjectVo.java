package com.house.project.business.domain.vo;

import com.house.project.business.domain.BizProject;
import com.house.project.business.domain.BizProjectImage;

import java.util.List;

public class BizProjectVo {

    /**
     * 楼盘
     */
    private BizProject project;

    /**
     * 楼盘照片列表
     */
    List<BizProjectImage> projectImages;


    public BizProject getProject() {
        return project;
    }

    public void setProject(BizProject project) {
        this.project = project;
    }

    public List<BizProjectImage> getProjectImages() {
        return projectImages;
    }

    public void setProjectImages(List<BizProjectImage> projectImages) {
        this.projectImages = projectImages;
    }
}
