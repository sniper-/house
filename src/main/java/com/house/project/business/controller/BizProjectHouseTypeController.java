package com.house.project.business.controller;

import java.util.List;

import com.house.common.utils.StringUtils;
import com.house.project.business.domain.vo.BizProjectHouseTypeVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.house.framework.aspectj.lang.annotation.Log;
import com.house.framework.aspectj.lang.enums.BusinessType;
import com.house.project.business.domain.BizProjectHouseType;
import com.house.project.business.service.IBizProjectHouseTypeService;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.domain.AjaxResult;
import com.house.common.utils.poi.ExcelUtil;
import com.house.framework.web.page.TableDataInfo;

/**
 * 楼盘户型管理Controller
 * 
 */
@CrossOrigin
@RestController
@RequestMapping("/business/ht")
public class BizProjectHouseTypeController extends BaseController
{
    @Autowired
    private IBizProjectHouseTypeService bizProjectHouseTypeService;

    /**
     * 查询楼盘户型管理列表
     */
    @ApiOperation("查询楼盘户型管理列表")
    @GetMapping("/list")
    public TableDataInfo list(BizProjectHouseTypeVo vo)
    {
        startPage();
        List<BizProjectHouseType> list = bizProjectHouseTypeService.selectBizProjectHouseTypeList(vo);
        return getDataTable(list);
    }

    /**
     * 导出楼盘户型管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:type:export')")
    @Log(title = "楼盘户型管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BizProjectHouseTypeVo vo)
    {
        List<BizProjectHouseType> list = bizProjectHouseTypeService.selectBizProjectHouseTypeList(vo);
        ExcelUtil<BizProjectHouseType> util = new ExcelUtil<BizProjectHouseType>(BizProjectHouseType.class);
        return util.exportExcel(list, "type");
    }

    /**
     * 获取楼盘户型管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:type:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bizProjectHouseTypeService.selectBizProjectHouseTypeById(id));
    }

    /**
     * 新增楼盘户型管理
     */
    @PreAuthorize("@ss.hasPermi('business:type:add')")
    @Log(title = "楼盘户型管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizProjectHouseType bizProjectHouseType)
    {
        if (StringUtils.isNull(bizProjectHouseType)) {
            return AjaxResult.error("户型信息为空");
        }
        if (StringUtils.isEmpty(bizProjectHouseType.getProjectHouseImageUrl())) {
            return AjaxResult.error("户型照片地址为空");
        }
        return toAjax(bizProjectHouseTypeService.insertBizProjectHouseType(bizProjectHouseType));
    }

    /**
     * 修改楼盘户型管理
     */
    @PreAuthorize("@ss.hasPermi('business:type:edit')")
    @Log(title = "楼盘户型管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizProjectHouseType bizProjectHouseType)
    {
        if (StringUtils.isNull(bizProjectHouseType)) {
            return AjaxResult.error("户型信息为空");
        }
        return toAjax(bizProjectHouseTypeService.updateBizProjectHouseType(bizProjectHouseType));
    }

    /**
     * 删除楼盘户型管理
     */
    @PreAuthorize("@ss.hasPermi('business:type:remove')")
    @Log(title = "楼盘户型管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        if (StringUtils.isEmpty(ids)) {
            return AjaxResult.error("请选择需要删除的户型");
        }
        return toAjax(bizProjectHouseTypeService.removeProjectHouseType(ids));
    }
}
