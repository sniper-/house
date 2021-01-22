package com.house.project.tool.swagger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.house.common.utils.bean.BeanUtils;
import com.house.project.business.domain.BizOrganization;
import com.house.project.business.service.IBizOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.house.common.utils.StringUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

/**
 * swagger 用户测试方法
 * 
 * @author vls-house
 */
@CrossOrigin
@Api("用户信息管理")
@RestController
@RequestMapping("/test/user")
public class TestController extends BaseController
{
    private final static Map<Integer, UserEntity> users = new LinkedHashMap<Integer, UserEntity>();
    {
        users.put(1, new UserEntity(1, "admin", "admin123", "15888888888"));
        users.put(2, new UserEntity(2, "ry", "admin123", "15666666666"));
    }

    @ApiOperation("获取用户列表")
    @GetMapping("/list")
    public AjaxResult userList()
    {
        List<UserEntity> userList = new ArrayList<UserEntity>(users.values());
        return AjaxResult.success(userList);
    }

    @ApiOperation("获取用户详细")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "path")
    @GetMapping("/{userId}")
    public AjaxResult getUser(@PathVariable Integer userId)
    {
        if (!users.isEmpty() && users.containsKey(userId))
        {
            return AjaxResult.success(users.get(userId));
        }
        else
        {
            return AjaxResult.error("用户不存在");
        }
    }

    @ApiOperation("新增用户")
    @ApiImplicitParam(name = "userEntity", value = "新增用户信息", dataType = "UserEntity")
    @PostMapping("/save")
    public AjaxResult save(UserEntity user)
    {
        if (StringUtils.isNull(user) || StringUtils.isNull(user.getUserId()))
        {
            return AjaxResult.error("用户ID不能为空");
        }
        return AjaxResult.success(users.put(user.getUserId(), user));
    }

    @ApiOperation("更新用户")
    @ApiImplicitParam(name = "userEntity", value = "新增用户信息", dataType = "UserEntity")
    @PutMapping("/update")
    public AjaxResult update(UserEntity user)
    {
        if (StringUtils.isNull(user) || StringUtils.isNull(user.getUserId()))
        {
            return AjaxResult.error("用户ID不能为空");
        }
        if (users.isEmpty() || !users.containsKey(user.getUserId()))
        {
            return AjaxResult.error("用户不存在");
        }
        users.remove(user.getUserId());
        return AjaxResult.success(users.put(user.getUserId(), user));
    }

    @ApiOperation("删除用户信息")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "path")
    @DeleteMapping("/{userId}")
    public AjaxResult delete(@PathVariable Integer userId)
    {
        if (!users.isEmpty() && users.containsKey(userId))
        {
            users.remove(userId);
            return AjaxResult.success();
        }
        else
        {
            return AjaxResult.error("用户不存在");
        }
    }

    @Autowired
    private IBizOrganizationService bizOrganizationService;

    @ApiOperation("新增机构")
    @ApiImplicitParam(name = "organizationEntity", value = "新增机构", dataType = "OrganizationEntity")
    @PostMapping("/saveOrganization")
    public AjaxResult save(OrganizationEntity organization)
    {
        BizOrganization bizOrganization = new BizOrganization();
        BeanUtils.copyBeanProp(bizOrganization, organization);
        return toAjax(bizOrganizationService.insertBizOrganization(bizOrganization));
    }
}



@ApiModel("用户实体")
class UserEntity
{
    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("用户手机")
    private String mobile;

    public UserEntity()
    {

    }

    public UserEntity(Integer userId, String username, String password, String mobile)
    {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.mobile = mobile;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }
}

@ApiModel("机构实体")
class OrganizationEntity
{
    /** 机构编码 */
    @ApiModelProperty("机构编码")
    private String organizationCode;

    /** 机构类型(1-中介机构 2-房产公司) */
    @ApiModelProperty("机构类型(1-中介机构 2-房产公司) ")
    private String organizationType;

    /** 机构名称 */
    @ApiModelProperty("机构名称")
    private String organizationName;

    /** 创建者 */
    @ApiModelProperty("创建者")
    private String createBy;


    /** 状态（0正常 1停用） */
    @ApiModelProperty("状态（0正常 1停用）")
    private String status;

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
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
