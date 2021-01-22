package com.house.project.business.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.house.common.enums.BizImageType;
import com.house.common.utils.StringUtils;
import com.house.common.utils.bean.BeanUtils;
import com.house.project.business.domain.BizProjectImage;
import com.house.project.business.domain.vo.BizProjectQueryVo;
import com.house.project.business.domain.vo.BizProjectVo;
import com.house.project.business.service.IBizProjectImageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.house.framework.aspectj.lang.annotation.Log;
import com.house.framework.aspectj.lang.enums.BusinessType;
import com.house.project.business.domain.BizProject;
import com.house.project.business.service.IBizProjectService;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.domain.AjaxResult;
import com.house.common.utils.poi.ExcelUtil;
import com.house.framework.web.page.TableDataInfo;

/**
 * 楼盘管理Controller
 * 
 */
@CrossOrigin
@RestController
@RequestMapping("/business/project")
public class BizProjectController extends BaseController
{
    @Autowired
    private IBizProjectService bizProjectService;
    @Autowired
    private IBizProjectImageService bizProjectImageService;

    /**
     * 查询楼盘管理列表
     */
    @ApiOperation("查询楼盘管理列表")
    @GetMapping("/list")
    public TableDataInfo list(BizProject bizProject)
    {
        startPage();
        List<BizProject> list = bizProjectService.selectBizProjectList(bizProject);
        List<BizProjectQueryVo> result = new ArrayList<>();
        if (!StringUtils.isEmpty(list)) {
            result = list.stream().map(this::buildProject).collect(Collectors.toList());
        }
        return getDataTable(result);
    }

    /**
     * 导出楼盘管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:project:export')")
    @Log(title = "楼盘管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BizProject bizProject)
    {
        List<BizProject> list = bizProjectService.selectBizProjectList(bizProject);
        ExcelUtil<BizProject> util = new ExcelUtil<BizProject>(BizProject.class);
        return util.exportExcel(list, "project");
    }

    /**
     * 获取楼盘管理详细信息-H5
     */
    @ApiOperation("获取楼盘管理详细信息-H5")
    @GetMapping(value = "/detail")
    public AjaxResult detail(BizProject project)
    {
        return AjaxResult.success(bizProjectService.selectBizProjectById(project.getId()));
    }

    /**
     * 获取楼盘管理详细信息-WEB
     */
    @PreAuthorize("@ss.hasPermi('business:project:query')")
    @ApiOperation("获取楼盘管理详细信息-WEB")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bizProjectService.selectBizProjectById(id));
    }


    /**
     * 新增楼盘管理
     */
    @ApiOperation("新增楼盘管理")
    @PreAuthorize("@ss.hasPermi('business:project:add')")
    @Log(title = "楼盘管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizProjectVo projectVo)
    {
        if (StringUtils.isNull(projectVo) || StringUtils.isNull(projectVo.getProject())) {
            return AjaxResult.error("楼盘信息为空");
        }
        return toAjax(bizProjectService.addProject(projectVo));
    }

    /**
     * 修改楼盘管理
     */
    @ApiOperation("修改楼盘管理")
    @PreAuthorize("@ss.hasPermi('business:project:edit')")
    @Log(title = "楼盘管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizProjectVo projectVo)
    {
        if (StringUtils.isNull(projectVo) || StringUtils.isNull(projectVo.getProject())) {
            return AjaxResult.error("楼盘信息为空");
        }
        return toAjax(bizProjectService.updateProject(projectVo));
    }

    /**
     * 删除楼盘管理
     */
    @PreAuthorize("@ss.hasPermi('business:project:remove')")
    @Log(title = "楼盘管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        if (StringUtils.isEmpty(ids)) {
            return AjaxResult.error("楼盘编号为空");
        }
        return toAjax(bizProjectService.removeProject(ids));
    }

    /**
     * 设置楼盘详细信息
     * @param project 楼盘
     * @return 结果
     */
    private BizProjectQueryVo buildProject(BizProject project) {
        BizProjectQueryVo vo = new BizProjectQueryVo();
        if (StringUtils.isNull(project)) {
            return vo;
        }
        BeanUtils.copyBeanProp(vo, project);
        // 查询楼盘对应的楼盘照片效果图，取第一张展现
        BizProjectImage image = new BizProjectImage();
        image.setProjectId(project.getId().toString());
        image.setImageType(BizImageType.XG.getCode());
        List<BizProjectImage> projectImages = bizProjectImageService.selectBizProjectImageList(image);
        if (!StringUtils.isEmpty(projectImages)) {
            BizProjectImage pi = projectImages.get(0);
            vo.setImageUrl(pi == null ? null : pi.getProjectImageUrl());
        }
        return vo;
    }
}
