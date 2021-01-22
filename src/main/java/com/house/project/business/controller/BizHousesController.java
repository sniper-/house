package com.house.project.business.controller;

import java.util.List;

import com.house.common.utils.ServletUtils;
import com.house.common.utils.StringUtils;
import com.house.framework.security.LoginUser;
import com.house.framework.security.service.TokenService;
import com.house.project.business.domain.vo.BizHousesVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.house.framework.aspectj.lang.annotation.Log;
import com.house.framework.aspectj.lang.enums.BusinessType;
import com.house.project.business.domain.BizHouses;
import com.house.project.business.service.IBizHousesService;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.domain.AjaxResult;
import com.house.common.utils.poi.ExcelUtil;
import com.house.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 房源管理Controller
 * 
 */
@CrossOrigin
@RestController
@RequestMapping("/business/houses")
public class BizHousesController extends BaseController
{
    @Autowired
    private IBizHousesService bizHousesService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询房源管理列表
     */
    @ApiOperation("查询房源管理列表")
    @PreAuthorize("@ss.hasPermi('business:houses:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizHouses bizHouses)
    {
        startPage();
        List<BizHousesVo> list = bizHousesService.selectBizHousesList(bizHouses);
        return getDataTable(list);
    }

    /**
     * 导出房源管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:houses:export')")
    @Log(title = "房源管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BizHouses bizHouses)
    {
        List<BizHousesVo> list = bizHousesService.selectBizHousesList(bizHouses);
        ExcelUtil<BizHousesVo> util = new ExcelUtil<>(BizHousesVo.class);
        return util.exportExcel(list, "房源数据");
    }

    /**
     * 获取房源管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:houses:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bizHousesService.selectBizHousesById(id));
    }

    /**
     * 新增房源管理
     */
    @PreAuthorize("@ss.hasPermi('business:houses:add')")
    @Log(title = "房源管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizHouses bizHouses)
    {
        if (StringUtils.isNull(bizHouses)) {
            return AjaxResult.error("房源信息为空");
        }
        return toAjax(bizHousesService.insertBizHouses(bizHouses));
    }

    /**
     * 修改房源管理
     */
    @PreAuthorize("@ss.hasPermi('business:houses:edit')")
    @Log(title = "房源管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizHouses bizHouses)
    {
        if (StringUtils.isNull(bizHouses)) {
            return AjaxResult.error("房源信息为空");
        }
        return toAjax(bizHousesService.updateBizHouses(bizHouses));
    }

    /**
     * 删除房源管理
     */
    @PreAuthorize("@ss.hasPermi('business:houses:remove')")
    @Log(title = "房源管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        if (StringUtils.isEmpty(ids)) {
            return AjaxResult.error("请选择需要删除的房源");
        }
        return toAjax(bizHousesService.removeHouse(ids));
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<BizHouses> util = new ExcelUtil<>(BizHouses.class);
        return util.importTemplateExcel("房源数据");
    }


    @Log(title = "房源管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('business:houses:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<BizHouses> util = new ExcelUtil<>(BizHouses.class);
        List<BizHouses> housesList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String message = bizHousesService.importHouses(housesList, loginUser.getUsername());
        return AjaxResult.success(message);
    }
}
