package com.house.project.business.controller;

import java.util.List;

import com.house.common.constant.Constants;
import com.house.common.enums.BizCheckStatus;
import com.house.common.enums.BizPostponeStatus;
import com.house.common.utils.DateUtils;
import com.house.common.utils.IdUtils;
import com.house.common.utils.SecurityUtils;
import com.house.common.utils.StringUtils;
import com.house.project.business.domain.BizReport;
import com.house.project.business.domain.vo.ReportPostPhoneVo;
import com.house.project.business.service.IBizReportService;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.house.framework.aspectj.lang.annotation.Log;
import com.house.framework.aspectj.lang.enums.BusinessType;
import com.house.project.business.domain.BizPostpone;
import com.house.project.business.service.IBizPostponeService;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.domain.AjaxResult;
import com.house.common.utils.poi.ExcelUtil;
import com.house.framework.web.page.TableDataInfo;

/**
 * 延期申请Controller
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
@CrossOrigin
@RestController
@RequestMapping("/business/postpone")
public class BizPostponeController extends BaseController
{
    @Autowired
    private IBizPostponeService bizPostponeService;
    @Autowired
    private IBizReportService bizReportService;

    /**
     * 报备信息延期登记
     * @param bizReport 报备id
     * @return 结果
     */
    @ApiOperation("报备信息延期登记")
    @Log(title = "报备信息延期登记", businessType = BusinessType.UPDATE)
    @PostMapping("/addPostpone")
    public AjaxResult addPostpone(@RequestBody BizReport bizReport) {
        BizReport report = bizReportService.selectBizReportById(bizReport.getId());
        if (StringUtils.isNull(report)) {
            return AjaxResult.error("报备信息不存在");
        }
        String username = SecurityUtils.getUsername();
        BizPostpone postpone = new BizPostpone();
        postpone.setPostponeNo(IdUtils.randomUUID());
        postpone.setReportNo(report.getReportNo());
        postpone.setApplicationTime(DateUtils.getNowDate());
        postpone.setCheckStatus(BizCheckStatus.NOT_APPROVE.getCode());
        postpone.setOriginalReportTime(report.getReportValidityTime());
        postpone.setCreateBy(username);
        return toAjax(bizPostponeService.addPostpone(bizReport.getId(), postpone));
    }

    /**
     * 报备信息延期审核
     * @param reportPost 参数
     * @return 结果
     */
    @PreAuthorize("@ss.hasPermi('business:postpone:approve')")
    @Log(title = "报备信息延期审核", businessType = BusinessType.UPDATE)
    @PutMapping("/approve")
    public AjaxResult approve(@RequestBody ReportPostPhoneVo reportPost) {
        BizReport report = bizReportService.selectBizReportById(reportPost.getId());
        if (StringUtils.isNull(report)) {
            return AjaxResult.error("报备信息不存在");
        }
        if (StringUtils.isEmpty(report.getPostponeStatus()) ||
                (!BizPostponeStatus.BE_REVIEWED.getCode().equals(report.getPostponeStatus()))) {
            return AjaxResult.error("延期审核状态必须是待审核");
        }
        //查询待审核的延期申请
        BizPostpone postpone = bizPostponeService.selectNotApproveData(report.getReportNo());
        if (StringUtils.isNull(postpone)) {
            return AjaxResult.error("没有待审核的延期申请信息");
        }
        //更新报备信息
        BizReport updReport = new BizReport();
        updReport.setId(report.getId());
        updReport.setReportValidityTime(reportPost.getReportValidTime()); //新报备有效时间
        updReport.setPostponeStatus(BizPostponeStatus.REVIEWED.getCode()); //报备有效期和延期状态为2-已审核
        updReport.setUpdateBy(SecurityUtils.getUsername());
        //更新延迟申请信息
        postpone.setCheckStatus(BizCheckStatus.APPROVE_SUCCESS.getCode());
        postpone.setCheckTime(DateUtils.getNowDate());
        postpone.setAddDays(reportPost.getAddDays());
        postpone.setNewReportTime(reportPost.getReportValidTime());
        postpone.setUpdateBy(SecurityUtils.getUsername());
        //数据更新
        return toAjax(bizPostponeService.approve(updReport, postpone));
    }

    /**
     * 查询延期申请列表
     */
    @PreAuthorize("@ss.hasPermi('business:bizPostpone:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizPostpone bizPostpone)
    {
        startPage();
        List<BizPostpone> list = bizPostponeService.selectBizPostponeList(bizPostpone);
        return getDataTable(list);
    }

    /**
     * 导出延期申请列表
     */
    @PreAuthorize("@ss.hasPermi('business:bizPostpone:export')")
    @Log(title = "延期申请", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BizPostpone bizPostpone)
    {
        List<BizPostpone> list = bizPostponeService.selectBizPostponeList(bizPostpone);
        ExcelUtil<BizPostpone> util = new ExcelUtil<BizPostpone>(BizPostpone.class);
        return util.exportExcel(list, "bizPostpone");
    }

    /**
     * 获取延期申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:bizPostpone:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bizPostponeService.selectBizPostponeById(id));
    }

    /**
     * 新增延期申请
     */
    @PreAuthorize("@ss.hasPermi('business:bizPostpone:add')")
    @Log(title = "延期申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizPostpone bizPostpone)
    {
        return toAjax(bizPostponeService.insertBizPostpone(bizPostpone));
    }

    /**
     * 修改延期申请
     */
    @PreAuthorize("@ss.hasPermi('business:bizPostpone:edit')")
    @Log(title = "延期申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizPostpone bizPostpone)
    {
        return toAjax(bizPostponeService.updateBizPostpone(bizPostpone));
    }

    /**
     * 删除延期申请
     */
    @PreAuthorize("@ss.hasPermi('business:bizPostpone:remove')")
    @Log(title = "延期申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bizPostponeService.deleteBizPostponeByIds(ids));
    }
}
