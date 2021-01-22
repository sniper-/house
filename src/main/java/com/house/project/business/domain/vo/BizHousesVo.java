package com.house.project.business.domain.vo;

import com.house.framework.aspectj.lang.annotation.Excel;
import com.house.project.business.domain.BizHouses;

public class BizHousesVo extends BizHouses {

    /** 户型名称 */
    @Excel(name = "户型名称", type = Excel.Type.EXPORT)
    private String projectHouseName;
    /** 户型名称 */
    @Excel(name = "楼盘名称", type = Excel.Type.EXPORT)
    private String projectName;

    public String getProjectHouseName() {
        return projectHouseName;
    }

    public void setProjectHouseName(String projectHouseName) {
        this.projectHouseName = projectHouseName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
