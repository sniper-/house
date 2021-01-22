package com.house.project.business.controller;

import java.util.List;

import com.house.common.utils.IdUtils;
import com.house.common.utils.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.house.framework.aspectj.lang.annotation.Log;
import com.house.framework.aspectj.lang.enums.BusinessType;
import com.house.project.business.domain.BizDimissionDistribution;
import com.house.project.business.service.IBizDimissionDistributionService;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.domain.AjaxResult;
import com.house.common.utils.poi.ExcelUtil;
import com.house.framework.web.page.TableDataInfo;

/**
 * 离职分配Controller
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
@CrossOrigin
@RestController
@RequestMapping("/business/bizDistribution")
public class BizDimissionDistributionController extends BaseController
{
    @Autowired
    private IBizDimissionDistributionService bizDimissionDistributionService;

    /**
     * 查询离职分配列表
     */
    @PreAuthorize("@ss.hasPermi('business:bizDistribution:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizDimissionDistribution bizDimissionDistribution)
    {
        startPage();
        List<BizDimissionDistribution> list = bizDimissionDistributionService.selectBizDimissionDistributionList(bizDimissionDistribution);
        return getDataTable(list);
    }

    /**
     * 导出离职分配列表
     */
    @PreAuthorize("@ss.hasPermi('business:bizDistribution:export')")
    @Log(title = "离职分配", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BizDimissionDistribution bizDimissionDistribution)
    {
        List<BizDimissionDistribution> list = bizDimissionDistributionService.selectBizDimissionDistributionList(bizDimissionDistribution);
        ExcelUtil<BizDimissionDistribution> util = new ExcelUtil<BizDimissionDistribution>(BizDimissionDistribution.class);
        return util.exportExcel(list, "bizDistribution");
    }

    /**
     * 获取离职分配详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:bizDistribution:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bizDimissionDistributionService.selectBizDimissionDistributionById(id));
    }

    /**
     * 新增离职分配
     */
    @PreAuthorize("@ss.hasPermi('business:bizDistribution:add')")
    @Log(title = "离职分配", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizDimissionDistribution bizDimissionDistribution)
    {
        return toAjax(bizDimissionDistributionService.insertBizDimissionDistribution(bizDimissionDistribution));
    }

    /**
     * 修改离职分配
     */
    @PreAuthorize("@ss.hasPermi('business:bizDistribution:edit')")
    @Log(title = "离职分配", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizDimissionDistribution bizDimissionDistribution)
    {
        return toAjax(bizDimissionDistributionService.updateBizDimissionDistribution(bizDimissionDistribution));
    }

    /**
     * 删除离职分配
     */
    @PreAuthorize("@ss.hasPermi('business:bizDistribution:remove')")
    @Log(title = "离职分配", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bizDimissionDistributionService.deleteBizDimissionDistributionByIds(ids));
    }
}
