package com.house.project.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.house.framework.aspectj.lang.annotation.Excel;
import com.house.framework.web.domain.BaseEntity;

/**
 * 业务用户对象 biz_user
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
public class BizUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增(用户ID) */
    private Integer id;

    /** 用户登录号 */
    @Excel(name = "用户登录号")
    private String loginName;

    /** 登录密码 */
    private String password;

    /** 用户姓名 */
    @Excel(name = "用户姓名")
    private String userName;

    /** 用户手机号 */
    @Excel(name = "用户手机号")
    private String phone;

    /** 用户角色:1-经纪人 2-店长 3-中介公司经理 4-前台 5-置业顾问 6-房产公司经理  */
    @Excel(name = "用户角色", readConverterExp = "1=经纪人,2=店长,3=中介公司经理,4=前台,5=置业顾问,6=房产公司经理")
    private String userRole;

    /** 用户状态: 0-在职 1-离职 */
    @Excel(name = "用户状态", readConverterExp = "0=在职,1=离职")
    private String userStatus;

    /** 所属机构编号 */
    @Excel(name = "所属机构编号")
    private String organizationCode;

    /** 上级人员编号 */
    @Excel(name = "上级人员编号")
    private Integer superiorCode;

    /** 所属楼盘号 */
    @Excel(name = "所属楼盘号")
    private Integer projectId;

    /** 密码错误次数，最大尝试次数5次 */
    private Integer wrongPasswordTimes;

    /** 上次的登陆时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上次的登陆时间", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    private Date lastLoginTime;

    /** 上次的登陆IP */
    @Excel(name = "上次的登陆IP", type = Excel.Type.EXPORT)
    private String lastLoginIp;

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

    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

    public String getLoginName() 
    {
        return loginName;
    }
    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPassword() 
    {
        return password;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setUserRole(String userRole)
    {
        this.userRole = userRole;
    }

    public String getUserRole()
    {
        return userRole;
    }
    public void setUserStatus(String userStatus) 
    {
        this.userStatus = userStatus;
    }

    public String getUserStatus() 
    {
        return userStatus;
    }
    public void setOrganizationCode(String organizationCode) 
    {
        this.organizationCode = organizationCode;
    }

    public String getOrganizationCode() 
    {
        return organizationCode;
    }
    public void setSuperiorCode(Integer superiorCode) 
    {
        this.superiorCode = superiorCode;
    }

    public Integer getSuperiorCode() 
    {
        return superiorCode;
    }
    public void setWrongPasswordTimes(Integer wrongPasswordTimes) 
    {
        this.wrongPasswordTimes = wrongPasswordTimes;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getWrongPasswordTimes()
    {
        return wrongPasswordTimes;
    }
    public void setLastLoginTime(Date lastLoginTime) 
    {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastLoginTime() 
    {
        return lastLoginTime;
    }
    public void setLastLoginIp(String lastLoginIp) 
    {
        this.lastLoginIp = lastLoginIp;
    }

    public String getLastLoginIp() 
    {
        return lastLoginIp;
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
            .append("loginName", getLoginName())
            .append("password", getPassword())
            .append("userName", getUserName())
            .append("phone", getPhone())
            .append("userRole", getUserRole())
            .append("userStatus", getUserStatus())
            .append("organizationCode", getOrganizationCode())
            .append("superiorCode", getSuperiorCode())
            .append("wrongPasswordTimes", getWrongPasswordTimes())
            .append("lastLoginTime", getLastLoginTime())
            .append("lastLoginIp", getLastLoginIp())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
