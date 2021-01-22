package com.house.project.business.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.house.common.enums.BizOrgType;
import com.house.common.enums.BizUserRole;
import com.house.common.utils.BaiduUtils;
import com.house.common.utils.ServletUtils;
import com.house.common.utils.StringUtils;
import com.house.framework.security.LoginUser;
import com.house.framework.security.service.TokenService;
import com.house.project.business.domain.BizUser;
import com.house.project.business.domain.vo.BizOrganizationVo;
import com.house.project.business.domain.vo.BizQueryOrgVo;
import com.house.project.business.domain.vo.SuperiorsVo;
import com.house.project.business.service.IBizUserService;
import com.house.project.system.domain.SysDept;
import com.house.project.system.service.ISysDeptService;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.house.framework.aspectj.lang.annotation.Log;
import com.house.framework.aspectj.lang.enums.BusinessType;
import com.house.project.business.domain.BizOrganization;
import com.house.project.business.service.IBizOrganizationService;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.domain.AjaxResult;
import com.house.common.utils.poi.ExcelUtil;
import com.house.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 机构管理Controller
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
@CrossOrigin
@RestController
@RequestMapping("/business/organization")
public class BizOrganizationController extends BaseController
{
    @Autowired
    private IBizOrganizationService bizOrganizationService;
    @Autowired
    private IBizUserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ISysDeptService deptService;

    @ApiOperation("机构/部门下拉列表")
    @GetMapping(value = "/queryOrg")
    public AjaxResult queryOrg(BizQueryOrgVo param) {
        if (StringUtils.isNull(param) || StringUtils.isNull(param.getUserRole())) {
            return AjaxResult.error("机构/部门下拉列表参数不能为空");
        }
        // 查询部门
        if (param.getUserRole().equals(BizUserRole.RECEPTION.getCode()) ||
                param.getUserRole().equals(BizUserRole.ADVISER.getCode()) ||
                param.getUserRole().equals(BizUserRole.COMPANY_MANAGER.getCode())) {
            List<SysDept> deptList = deptService.selectOneTwoDept();
            if (StringUtils.isEmpty(deptList)) {
                return AjaxResult.success();
            }
            return AjaxResult.success(deptList.stream().map(d ->
                    new BizQueryOrgVo(d.getDeptId().toString(), d.getDeptName())).collect(Collectors.toList()));
        }
        // 查询机构
        else {
            List<BizOrganizationVo> organizationList = bizOrganizationService.selectBizOrganizationList(null);
            if (StringUtils.isEmpty(organizationList)) {
                return AjaxResult.success();
            }
            return AjaxResult.success(organizationList.stream().map(o ->
                    new BizQueryOrgVo(o.getOrganizationCode(), o.getOrganizationName())).collect(Collectors.toList()));
        }
    }

    /**
     * 查询机构管理列表
     * @return 机构管理列表
     */
    @GetMapping("/all")
    public AjaxResult all() {
        return AjaxResult.success(bizOrganizationService.selectBizOrganizationList(null));
    }

    /**
     * 查询中介公司
     * @return 结果
     */
    @ApiOperation("查询中介公司")
    @PostMapping("/intermediary")
    public AjaxResult intermediary(@RequestBody BizOrganization bizOrganization) {
        bizOrganization.setOrganizationType(BizOrgType.INTERMEDIARY.getCode());
        return AjaxResult.success(bizOrganizationService.selectBizOrganizationList(bizOrganization));
    }


    /**
     * 查询机构管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:organization:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizOrganization bizOrganization)
    {
        startPage();
        List<BizOrganizationVo> list = bizOrganizationService.selectBizOrganizationList(bizOrganization);
        return getDataTable(list);
    }

    /**
     * 导出机构管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:organization:export')")
    @Log(title = "机构管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BizOrganization bizOrganization)
    {
        List<BizOrganizationVo> list = bizOrganizationService.selectBizOrganizationList(bizOrganization);
        ExcelUtil<BizOrganizationVo> util = new ExcelUtil<>(BizOrganizationVo.class);
        return util.exportExcel(list, "bizOrganization");
    }

    /**
     * 获取机构管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:organization:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bizOrganizationService.selectBizOrganizationById(id));
    }

    /**
     * 新增机构管理
     */
    @PreAuthorize("@ss.hasPermi('business:organization:add')")
    @Log(title = "机构管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizOrganization bizOrganization)
    {
        return toAjax(bizOrganizationService.insertBizOrganization(bizOrganization));
    }

    /**
     * 修改机构管理
     */
    @PreAuthorize("@ss.hasPermi('business:organization:edit')")
    @Log(title = "机构管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizOrganization bizOrganization)
    {
        BizOrganization params = new BizOrganization();
        String orgName = bizOrganization.getOrganizationName();
        // 根据机构名称查询结构
        params.setOrganizationName(orgName);
        List<BizOrganizationVo> bizOrganizations = bizOrganizationService.selectBizOrganizationList(params);
        // 验证机构名称是否重复
        if (!StringUtils.isEmpty(bizOrganizations)) {
            for (BizOrganization org : bizOrganizations) {
                if (org.getOrganizationName().equals(orgName) && !org.getId().equals(bizOrganization.getId())) {
                    return AjaxResult.error("修改机构管理失败，机构名称'" + orgName + "'已经存在");
                }
            }
        }
        return toAjax(bizOrganizationService.updateBizOrganization(bizOrganization));
    }

    /**
     * 删除机构管理
     */
    @PreAuthorize("@ss.hasPermi('business:organization:remove')")
    @Log(title = "机构管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        SuperiorsVo vo = new SuperiorsVo();
        for (Integer id : ids) {
            BizOrganization organization = bizOrganizationService.selectBizOrganizationById(id);
            if (StringUtils.isNull(organization) || StringUtils.isEmpty(organization.getOrganizationCode())) {
                continue;
            }
            vo.setOrganizationCode(organization.getOrganizationCode());
            List<BizUser> users = userService.selectSuperiors(vo);
            if (!StringUtils.isEmpty(users)) {
                return AjaxResult.error("机构编号'" + organization.getOrganizationCode() + "'存在业务用户, 无法执行删除操作");
            }
        }
        return toAjax(bizOrganizationService.updateBizOrganizationDelStatus(ids));
    }


    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<BizOrganization> util = new ExcelUtil<>(BizOrganization.class);
        return util.importTemplateExcel("机构数据");
    }

    @Log(title = "机构管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('business:organization:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<BizOrganization> util = new ExcelUtil<>(BizOrganization.class);
        List<BizOrganization> organizationList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String message = bizOrganizationService.importOrganization(organizationList, loginUser.getUsername());
        return AjaxResult.success(message);
    }
}
