package com.house.project.business.controller;

import com.house.common.enums.BizUserStatus;
import java.util.List;

import com.house.common.constant.Constants;
import com.house.common.enums.BizUserRole;
import com.house.common.utils.SecurityUtils;
import com.house.common.utils.ServletUtils;
import com.house.common.utils.StringUtils;
import com.house.framework.security.LoginBody;
import com.house.framework.security.LoginUser;
import com.house.framework.security.service.TokenService;
import com.house.project.business.domain.BizReport;
import com.house.project.business.domain.vo.BizUserVo;
import com.house.project.business.domain.vo.RegisterVo;
import com.house.project.business.domain.vo.SuperiorsVo;
import com.house.project.business.service.IBizReportService;
import com.house.project.system.service.ISysConfigService;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.house.framework.aspectj.lang.annotation.Log;
import com.house.framework.aspectj.lang.enums.BusinessType;
import com.house.project.business.domain.BizUser;
import com.house.project.business.service.IBizUserService;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.domain.AjaxResult;
import com.house.common.utils.poi.ExcelUtil;
import com.house.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;


/**
 * 业务用户Controller
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
@CrossOrigin
@RestController
@RequestMapping("/business/user")
public class BizUserController extends BaseController
{
    @Autowired
    private IBizUserService userService;
    @Autowired
    private IBizReportService reportService;

    @Autowired
    private ISysConfigService configService;
    @Autowired
    private TokenService tokenService;

    /**
     * 登录方法
     *
     * @param loginBody 登陆信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = userService.loginByBizUser(loginBody.getUsername(), loginBody.getPassword());
        BizUser user = userService.selectBizUserByName(loginBody.getUsername());
        if (BizUserStatus.QUIT.getCode().equals(user.getUserStatus())) {
            return AjaxResult.error("该用户已经离职不能登录！");
        }

        if ("1".equals(user.getStatus())) {
            return AjaxResult.error("该用户已经停用不能登录！");
        }

        ajax.put(Constants.TOKEN, token);
        ajax.put(Constants.USER_ROLE, user);
        return ajax;
    }

    /**
     * 重置密码
     * @param register 参数
     * @return 结果
     */
    @ApiOperation("重置密码")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody RegisterVo register)
    {
        String loginName = register.getUser().getLoginName();
        BizUser user = userService.selectBizUserByName(loginName);
        if (StringUtils.isNull(user)) {
            return AjaxResult.error("重置密码用户：" + loginName + " 不存在");
        }
        user.setPassword(SecurityUtils.encryptPassword(register.getUser().getPassword()));
        user.setUpdateBy(loginName);
        AjaxResult ajaxResult = userService.verifyResult(register);
        if (!StringUtils.isNull(ajaxResult)) {
            return ajaxResult;
        }
        return toAjax(userService.updateBizUser(user));
    }

    /**
     * 注册
     * @param register 注册业务用户
     * @return 结果
     */
    @ApiOperation("注册业务用户")
    @Log(title = "注册业务用户", businessType = BusinessType.INSERT)
    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterVo register)
    {
        return userService.registerUser(register, false);
    }

    /**
     * 查询业务用户上级集合
     *
     * @param superiors 查询条件
     * @return 业务用户上级集合
     */
    @GetMapping(value = "/querySuperiors")
    public AjaxResult querySuperiors(SuperiorsVo superiors)
    {
        String userRole = superiors.getUserRole();
        if (StringUtils.isNull(userRole)) {
            return AjaxResult.error("查询上级姓名失败，用户角色为空");
        }
        if (!userRole.equals(BizUserRole.AGENT.getCode()) && !userRole.equals(BizUserRole.ADVISER.getCode())) {
            return AjaxResult.success();
        }
        return AjaxResult.success(userService.selectSuperiors(superiors));
    }

    /**
     * 查询业务用户列表
     */
    @PreAuthorize("@ss.hasPermi('business:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizUser bizUser)
    {
        startPage();
        List<BizUserVo> list = userService.selectBizUserList(bizUser);
        return getDataTable(list);
    }

    /**
     * 导出业务用户列表
     */
    @PreAuthorize("@ss.hasPermi('business:user:export')")
    @Log(title = "业务用户", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BizUser bizUser)
    {
        List<BizUserVo> list = userService.selectBizUserList(bizUser);
        list.forEach(user -> user.setOrganizationName(user.getOrganizationName()));
        ExcelUtil<BizUserVo> util = new ExcelUtil<>(BizUserVo.class);
        return util.exportExcel(list, "业务用户");
    }

    /**
     * 获取业务用户详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:user:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(userService.selectBizUserById(id));
    }


    /**
     * 获取当前登录用户信息
     * @return 结果
     */
    @ApiOperation("获取当前登录用户信息")
    @GetMapping("/queryCurrentUser")
    public AjaxResult queryCurrentUser() {
        BizUser user;
        try {
            user = SecurityUtils.getLoginUser().getBizUser();
        } catch (Exception e) {
            return AjaxResult.error("获取当前登录用户异常: " + e.getMessage());
        }
        if (StringUtils.isNull(user) || StringUtils.isNull(user.getId())) {
            return AjaxResult.error("用户已过期，请重新登录");
        }
        return AjaxResult.success(userService.selectBizUserById(user.getId()));
    }

    /**
     * 新增业务用户
     */
    @PreAuthorize("@ss.hasPermi('business:user:add')")
    @Log(title = "业务用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizUser bizUser)
    {
        String password = configService.selectConfigByKey("sys.user.initPassword");
        bizUser.setPassword(password);
        return toAjax(userService.addUser(bizUser, true));
    }



    /**
     * 修改业务用户
     */
    @PreAuthorize("@ss.hasPermi('business:user:edit')")
    @Log(title = "业务用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizUser bizUser)
    {
        BizUserVo user = userService.selectBizUserById(bizUser.getId());
        if (StringUtils.isNull(user)) {
            return AjaxResult.error("修改业务用户失败，用户不存在");
        }
        // 验证登录号是否重复
        if (!bizUser.getLoginName().equals(user.getLoginName())) {
            BizUser checkName = userService.selectBizUserByName(bizUser.getLoginName());
            if (!StringUtils.isNull(checkName) && !checkName.getId().equals(user.getId())) {
                return AjaxResult.error("修改业务用户'" + bizUser.getLoginName() + "'失败，登录账号已存在");
            }
        }

        if (!StringUtils.isEmpty(bizUser.getPhone()) && !bizUser.getPhone().equals(user.getPhone())) {
            BizUser checkPhone = userService.selectBizUserByPhone(bizUser.getPhone());
            if (!StringUtils.isNull(checkPhone) && !checkPhone.getId().equals(user.getId())) {
                return AjaxResult.error("修改业务用户'" + bizUser.getLoginName() + "'失败，手机号码已存在");
            }
        }
        bizUser.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.updateBizUser(bizUser));
    }

    /**
     * 删除业务用户
     */
    @PreAuthorize("@ss.hasPermi('business:user:remove')")
    @Log(title = "业务用户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        BizReport reportParams = new BizReport();
        BizUser userParams = new BizUser();
        //验证用户是否可以删除
        for (Integer id : ids) {
            BizUserVo user = userService.selectBizUserById(id);
            if (StringUtils.isNull(user) || StringUtils.isNull(user.getUserRole())) {
                continue;
            }
            String userRole = user.getUserRole(); //用户角色
            //经纪人
            if (userRole.equals(BizUserRole.AGENT.getCode())) {
                reportParams.setMiddlemanUserId(id);
                reportParams.setConsultantUserId(null);
                List<BizReport> bizReports = reportService.selectBizReportList(reportParams);
                if (!StringUtils.isEmpty(bizReports)) {
                    return AjaxResult.error("删除经纪人'" + user.getLoginName() + "'失败，存在未完成的业务数据");
                }
            }
            //置业顾问
            else if (userRole.equals(BizUserRole.ADVISER.getCode())) {
                reportParams.setConsultantUserId(id);
                reportParams.setMiddlemanUserId(null);
                List<BizReport> bizReports = reportService.selectBizReportList(reportParams);
                if (!StringUtils.isEmpty(bizReports)) {
                    return AjaxResult.error("删除置业顾问'" + user.getLoginName() + "'失败，存在未完成的业务数据");
                }
            }
            //店长.中介公司经理.房产公司经理
            else if(userRole.equals(BizUserRole.OWNER.getCode()) ||
                    userRole.equals(BizUserRole.MEDIUM_MANAGER.getCode()) ||
                    userRole.equals(BizUserRole.COMPANY_MANAGER.getCode())) {
                userParams.setSuperiorCode(id);
                List<BizUserVo> superiors = userService.selectBizUserList(userParams);
                if (!StringUtils.isEmpty(superiors)) {
                    return AjaxResult.error("删除'" + user.getLoginName() + "'失败，该业务用户存在在职下属");
                }
            }
        }
        return toAjax(userService.updateBizUserDelStatus(ids));
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<BizUser> util = new ExcelUtil<>(BizUser.class);
        return util.importTemplateExcel("业务用户数据");
    }


    @Log(title = "业务用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('business:user:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<BizUser> util = new ExcelUtil<>(BizUser.class);
        List<BizUser> userList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String message = userService.importBizUser(userList, loginUser.getUsername());
        return AjaxResult.success(message);
    }
}
