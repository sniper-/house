package com.house.project.business.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.house.common.utils.StringUtils;
import com.house.framework.aspectj.lang.annotation.Excel;
import com.house.framework.web.domain.BaseEntity;
import com.house.project.business.domain.BizUser;

import java.util.Date;

public class BizUserVo extends BizUser {

    /** 机构名称 */
    @Excel(name = "机构名称", type = Excel.Type.EXPORT)
    private String organizationName;

    /** 部门名称 */
    private String deptName;

    /** 上级姓名 */
    @Excel(name = "上级姓名", type = Excel.Type.EXPORT)
    private String superiorName;

    /** 楼盘名称 */
    @Excel(name = "楼盘名称", type = Excel.Type.EXPORT)
    private String projectName;


    public String getOrganizationName() {
        return StringUtils.isEmpty(deptName) ? organizationName : deptName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSuperiorName() {
        return superiorName;
    }

    public void setSuperiorName(String superiorName) {
        this.superiorName = superiorName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
