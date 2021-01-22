package com.house.project.business.controller;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.house.framework.aspectj.lang.annotation.Log;
import com.house.framework.aspectj.lang.enums.BusinessType;
import com.house.project.business.domain.BizProjectImage;
import com.house.project.business.service.IBizProjectImageService;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.domain.AjaxResult;
import com.house.common.utils.poi.ExcelUtil;
import com.house.framework.web.page.TableDataInfo;

/**
 * 楼盘照片管理Controller
 * 
 */
@CrossOrigin
@RestController
@RequestMapping("/business/image")
public class BizProjectImageController extends BaseController
{
    @Autowired
    private IBizProjectImageService bizProjectImageService;

    /**
     * 查询楼盘照片管理列表
     */
    @ApiOperation("查询楼盘照片管理列表")
    @GetMapping("/list")
    public TableDataInfo list(BizProjectImage bizProjectImage)
    {
        startPage();
        List<BizProjectImage> list = bizProjectImageService.selectBizProjectImageList(bizProjectImage);
        return getDataTable(list);
    }

    /**
     * 导出楼盘照片管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:image:export')")
    @Log(title = "楼盘照片管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BizProjectImage bizProjectImage)
    {
        List<BizProjectImage> list = bizProjectImageService.selectBizProjectImageList(bizProjectImage);
        ExcelUtil<BizProjectImage> util = new ExcelUtil<BizProjectImage>(BizProjectImage.class);
        return util.exportExcel(list, "image");
    }

    /**
     * 获取楼盘照片管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:image:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bizProjectImageService.selectBizProjectImageById(id));
    }

    /**
     * 新增楼盘照片管理
     */
    @PreAuthorize("@ss.hasPermi('business:image:add')")
    @Log(title = "楼盘照片管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizProjectImage bizProjectImage)
    {
        return toAjax(bizProjectImageService.insertBizProjectImage(bizProjectImage));
    }

    /**
     * 修改楼盘照片管理
     */
    @PreAuthorize("@ss.hasPermi('business:image:edit')")
    @Log(title = "楼盘照片管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizProjectImage bizProjectImage)
    {
        return toAjax(bizProjectImageService.updateBizProjectImage(bizProjectImage));
    }

    /**
     * 删除楼盘照片管理
     */
    @PreAuthorize("@ss.hasPermi('business:image:remove')")
    @Log(title = "楼盘照片管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bizProjectImageService.deleteBizProjectImageByIds(ids));
    }
}
