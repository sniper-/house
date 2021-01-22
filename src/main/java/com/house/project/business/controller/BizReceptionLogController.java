package com.house.project.business.controller;

import java.util.List;

import com.house.common.utils.StringUtils;
import com.house.project.business.domain.BizReport;
import com.house.project.business.domain.vo.BizUserVo;
import com.house.project.business.service.IBizReportService;
import com.house.project.business.service.IBizUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.house.framework.aspectj.lang.annotation.Log;
import com.house.framework.aspectj.lang.enums.BusinessType;
import com.house.project.business.domain.BizReceptionLog;
import com.house.project.business.service.IBizReceptionLogService;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.domain.AjaxResult;
import com.house.common.utils.poi.ExcelUtil;
import com.house.framework.web.page.TableDataInfo;

/**
 * 接待日志Controller
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
@CrossOrigin
@RestController
@RequestMapping("/business/log")
public class BizReceptionLogController extends BaseController
{
    @Autowired
    private IBizReceptionLogService bizReceptionLogService;
    @Autowired
    private IBizUserService userService;
    @Autowired
    private IBizReportService reportService;

    /**
     * 查询接待日志列表
     */
    @GetMapping(value = "/all")
    public AjaxResult all(String reportNo)
    {
        BizReceptionLog params = new BizReceptionLog();

        params.setReportNo(reportNo);
        List<BizReceptionLog> bizReceptionLogs = bizReceptionLogService.selectBizReceptionLogList(params);
        bizReceptionLogs.forEach(receptionLog -> {
            BizUserVo consultant = userService.selectBizUserById(receptionLog.getConsultantUserId());//置业顾问
            receptionLog.setTempValue(consultant == null ? null : consultant.getUserName());
        });
        return AjaxResult.success(bizReceptionLogs);
    }

    /**
     * 查询接待日志列表
     */
    @ApiOperation("查询接待日志列表")
    @PreAuthorize("@ss.hasPermi('business:bizLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizReceptionLog bizReceptionLog)
    {
        startPage();
        List<BizReceptionLog> list = bizReceptionLogService.selectBizReceptionLogList(bizReceptionLog);
        return getDataTable(list);
    }

    /**
     * 导出接待日志列表
     */
    @PreAuthorize("@ss.hasPermi('business:bizLog:export')")
    @Log(title = "接待日志", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BizReceptionLog bizReceptionLog)
    {
        List<BizReceptionLog> list = bizReceptionLogService.selectBizReceptionLogList(bizReceptionLog);
        ExcelUtil<BizReceptionLog> util = new ExcelUtil<BizReceptionLog>(BizReceptionLog.class);
        return util.exportExcel(list, "bizLog");
    }

    /**
     * 获取接待日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:bizLog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bizReceptionLogService.selectBizReceptionLogById(id));
    }

    /**
     * 新增接待日志
     */
    @ApiOperation("新增接待日志")
    @PreAuthorize("@ss.hasPermi('business:bizLog:add')")
    @Log(title = "接待日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizReceptionLog bizReceptionLog)
    {
        if (StringUtils.isNull(bizReceptionLog) || StringUtils.isEmpty(bizReceptionLog.getReportNo())) {
            return AjaxResult.error("报备编号不能为空");
        }
        BizReport report = reportService.selectBizReportByReportNo(bizReceptionLog.getReportNo());
        if (StringUtils.isNull(report)) {
            return AjaxResult.error("新增接待日志'" + bizReceptionLog.getReportNo() + "'失败，报备记录不存在");
        }
        return toAjax(bizReceptionLogService.insertBizReceptionLog(bizReceptionLog));
    }

    /**
     * 修改接待日志
     */
    @PreAuthorize("@ss.hasPermi('business:bizLog:edit')")
    @Log(title = "接待日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizReceptionLog bizReceptionLog)
    {
        return toAjax(bizReceptionLogService.updateBizReceptionLog(bizReceptionLog));
    }

    /**
     * 删除接待日志
     */
    @PreAuthorize("@ss.hasPermi('business:bizLog:remove')")
    @Log(title = "接待日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bizReceptionLogService.deleteBizReceptionLogByIds(ids));
    }
}
