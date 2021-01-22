package com.house.project.business.controller;

import java.util.List;

import com.house.common.utils.SecurityUtils;
import com.house.common.utils.StringUtils;
import com.house.project.business.domain.BizProject;
import com.house.project.system.domain.SysUser;
import com.house.project.system.service.ISysDeptService;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.house.framework.aspectj.lang.annotation.Log;
import com.house.framework.aspectj.lang.enums.BusinessType;
import com.house.project.business.domain.BizCollect;
import com.house.project.business.service.IBizCollectService;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.domain.AjaxResult;
import com.house.common.utils.poi.ExcelUtil;
import com.house.framework.web.page.TableDataInfo;

/**
 * 统计管理Controller
 * 
 * @author bo.zhang
 * @date 2020-08-12
 */
@CrossOrigin
@RestController
@RequestMapping("/business/collect")
public class BizCollectController extends BaseController
{
    @Autowired
    private IBizCollectService bizCollectService;
    @Autowired
    private ISysDeptService deptService;

    /**
     * 首页数据统计
     *
     * @return 结果
     */
    @ApiOperation("首页数据统计")
    @GetMapping(value = "/queryCollect")
    public AjaxResult queryAdvisers(BizProject project)
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        if (user == null || StringUtils.isNull(user.getUserId())) {
            return AjaxResult.error("没有登录用户");
        }
        return AjaxResult.success(bizCollectService.queryCollect(project.getId(), user.getUserId()));
    }

    /**
     * 查询统计管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:collect:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizCollect bizCollect)
    {
        startPage();
        List<BizCollect> list = bizCollectService.selectBizCollectList(bizCollect);
        return getDataTable(list);
    }

    /**
     * 导出统计管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:collect:export')")
    @Log(title = "统计管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BizCollect bizCollect)
    {
        List<BizCollect> list = bizCollectService.selectBizCollectList(bizCollect);
        ExcelUtil<BizCollect> util = new ExcelUtil<BizCollect>(BizCollect.class);
        return util.exportExcel(list, "collect");
    }

    /**
     * 获取统计管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:collect:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bizCollectService.selectBizCollectById(id));
    }

    /**
     * 新增统计管理
     */
    @PreAuthorize("@ss.hasPermi('business:collect:add')")
    @Log(title = "统计管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizCollect bizCollect)
    {
        return toAjax(bizCollectService.insertBizCollect(bizCollect));
    }

    /**
     * 修改统计管理
     */
    @PreAuthorize("@ss.hasPermi('business:collect:edit')")
    @Log(title = "统计管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizCollect bizCollect)
    {
        return toAjax(bizCollectService.updateBizCollect(bizCollect));
    }

    /**
     * 删除统计管理
     */
    @PreAuthorize("@ss.hasPermi('business:collect:remove')")
    @Log(title = "统计管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bizCollectService.deleteBizCollectByIds(ids));
    }
}
