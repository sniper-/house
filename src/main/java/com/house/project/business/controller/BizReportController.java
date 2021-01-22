package com.house.project.business.controller;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.house.common.enums.BizReportStatus;
import com.house.common.enums.BizUserRole;
import com.house.common.enums.BizUserStatus;
import com.house.common.enums.BizValidStatus;
import com.house.common.exception.CustomException;
import com.house.common.utils.DateUtils;
import com.house.common.utils.SecurityUtils;
import com.house.common.utils.ServletUtils;
import com.house.common.utils.StringUtils;
import com.house.common.utils.bean.BeanUtils;
import com.house.framework.security.LoginUser;
import com.house.framework.security.service.TokenService;
import com.house.project.business.domain.*;
import com.house.project.business.domain.vo.BizReportVo;
import com.house.project.business.domain.vo.BizUserVo;
import com.house.project.business.service.*;
import com.house.project.system.domain.SysRole;
import com.house.project.system.service.ISysDeptService;
import com.house.project.system.service.ISysRoleService;
import io.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.house.framework.aspectj.lang.annotation.Log;
import com.house.framework.aspectj.lang.enums.BusinessType;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.domain.AjaxResult;
import com.house.common.utils.poi.ExcelUtil;
import com.house.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 报备管理Controller
 *
 * @author bo.zhang
 * @date 2020-07-16
 */
@CrossOrigin
@RestController
@RequestMapping("/business/report")
public class BizReportController extends BaseController
{
    @Autowired
    private IBizReportService bizReportService;
    @Autowired
    private IBizUserService userService;
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private TokenService tokenService;


    /**
     * 客户评级
     * @param report 报备编号和客户评级
     * @return 结果
     */
    @Log(title = "客户评级", businessType = BusinessType.UPDATE)
    @PutMapping("/customerRating")
    public AjaxResult customerRating(@RequestBody BizReport report)
    {
        if (StringUtils.isNull(report) ||
                StringUtils.isEmpty(report.getReportNo()) ||
                StringUtils.isEmpty(report.getCustomerRating())) {
            return AjaxResult.error("报备编号和客户评级不能为空");
        }
        BizReport bizReport = bizReportService.selectBizReportByReportNo(report.getReportNo());
        if (StringUtils.isNull(bizReport)) {
            return AjaxResult.error("报备编号[" + report.getReportNo() + "]的报备信息不存在");
        }
        bizReport.setCustomerRating(report.getCustomerRating()); // 客户评级
        bizReport.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(bizReportService.updateBizReport(bizReport));
    }

    /**
     * 分配置业顾问
     * @param report 报备编号和置业顾问编号
     * @return 结果
     */
    @Log(title = "分配置业顾问", businessType = BusinessType.UPDATE)
    @PutMapping("/allotConsultant")
    public AjaxResult allotConsultant(@RequestBody BizReport report)
    {
        if (StringUtils.isNull(report) ||
            StringUtils.isEmpty(report.getReportNo()) ||
            StringUtils.isNull(report.getConsultantUserId())) {
            return AjaxResult.error("报备编号和置业顾问编号不能为空");
        }
        BizUserVo bizUser = userService.selectBizUserById(report.getConsultantUserId());
        if (StringUtils.isNull(bizUser)) {
            return AjaxResult.error("置业顾问不存在");
        }
        BizReport bizReport = bizReportService.selectBizReportByReportNo(report.getReportNo());
        if (StringUtils.isNull(bizReport)) {
            return AjaxResult.error("报备编号[" + report.getReportNo() + "]的报备信息不存在");
        }
        bizReport.setFirstVisitTime(DateUtils.getNowDate()); // 次到访时间
        bizReport.setVisitTimes(1); // 到访次数设为1
        bizReport.setConsultantUserId(report.getConsultantUserId()); // 置业顾问编号
        bizReport.setUpdateBy(SecurityUtils.getUsername());
        bizReport.setReportStatus(BizReportStatus.VISIT.getCode());
        // 当首次到访时间距离报备预约时间前后超过2小时设置为报备无效
        // 第一次到访时间-报备时间小于30分钟以内报备无效
        long hourPoor = DateUtils.getHourPoor(bizReport.getAppointmentTime(), bizReport.getFirstVisitTime());
        long minPoor = DateUtils.getMinPoor(bizReport.getFirstVisitTime(), bizReport.getReportTime());
        if (Math.abs(hourPoor) > 2 || minPoor < 30) {
            bizReport.setValidStatus(BizValidStatus.INVALID.getCode());
        }
        return toAjax(bizReportService.updateBizReport(bizReport));
    }

    /**
     * 楼盘编号查询置业顾问列表
     *
     * @return 置业顾问列表
     */
    @ApiOperation("楼盘编号查询置业顾问列表")
    @GetMapping(value = "/queryAdvisers")
    public AjaxResult queryAdvisers(Integer projectId)
    {
        BizUser params = new BizUser();
        params.setUserRole(BizUserRole.ADVISER.getCode());
        params.setProjectId(projectId);
        return AjaxResult.success(userService.selectBizUserList(params));
    }


    /**
     * 离职分配批量新增
     * @param distributions 离职分配列表
     * @return 结果
     */
    @PreAuthorize("@ss.hasPermi('business:report:distributions')")
    @Log(title = "离职分配批量新增", businessType = BusinessType.UPDATE)
    @PutMapping("/distribution")
    public AjaxResult distribution(@RequestBody List<BizDimissionDistribution> distributions)
    {
        if (StringUtils.isEmpty(distributions)) {
            return AjaxResult.error("离职分配列表不能为空");
        }
        bizReportService.distribution(distributions);
        return toAjax(distributions.size());
    }

    /**
     * 置业顾问离职
     * @param consultantId 置业顾问id
     * @return 结果
     */
    @Log(title = "离职", businessType = BusinessType.UPDATE)
    @PutMapping("/quit")
    public AjaxResult quit(@RequestBody Integer consultantId)
    {
        List<BizReportVo> list = bizReportService.selectByConsultantUserId(consultantId);
        if (!StringUtils.isEmpty(list)) {
            return AjaxResult.error("该置业顾问存在处理中的备案信息不能进行离职操作");
        }
        BizUser user = new BizUser();
        user.setId(consultantId);
        user.setUserStatus(BizUserStatus.QUIT.getCode());
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.updateBizUser(user));
    }

    /**
     * 查询置业顾问对应的报备列表
     * @param consultantId 置业顾问id
     * @return 结果
     */
    @GetMapping("/queryConsultantReport")
    public AjaxResult queryConsultantReport(Integer consultantId)
    {
        List<BizReportVo> list = bizReportService.selectByConsultantUserId(consultantId);
        return AjaxResult.success(list);
    }

    /**
     * 查询置业顾问列表
     *
     * @return 置业顾问列表
     */
    @GetMapping(value = "/queryConsultants")
    public AjaxResult queryConsultants(Integer consultantId)
    {
        BizUser params = new BizUser();
        params.setUserRole(BizUserRole.ADVISER.getCode());
        List<BizUserVo> users = userService.selectBizUserList(params);
        //过滤自己
        if (!StringUtils.isNull(consultantId)) {
            for (BizUserVo user : users) {
                if (user.getId().equals(consultantId)) {
                    users.remove(user);
                }
            }
        }
        return AjaxResult.success(users);
    }

    /**
     * 查询经纪人列表
     *
     * @return 经纪人列表
     */
    @GetMapping(value = "/queryAgents")
    public AjaxResult queryAgents()
    {
        BizUser params = new BizUser();
        params.setUserRole(BizUserRole.AGENT.getCode());
        return AjaxResult.success(userService.selectBizUserList(params));
    }

    /**
     * 查询报备/预约管理列表
     */
    @ApiOperation("查询报备/预约管理列表")
    @PreAuthorize("@ss.hasPermi('business:report:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizReport bizReport)
    {
        List<BizReportVo> list;
        // H5端报备查询
        if (StringUtils.isNull(SecurityUtils.getLoginUser().getUser())) {
            BizUser user = SecurityUtils.getLoginUser().getBizUser();
            // 根据部门号查询业务用户部分下的所有用户id
            Integer[] ids = bizReportService.queryUserIdsByOrg(user);
            bizReport.setIds(ids);
            startPage();
            list = bizReportService.selectH5ReportList(bizReport, user);
        }
        // WEB端报备查询
        else {
            // 判断当前登录用户是否渠道专员
            List<SysRole> sysRoles = roleService.selectRoleByUserId(SecurityUtils.getLoginUser().getUser().getUserId());
            startPage();
            //public List<BizReportVo> selectReportDetail(BizReport report);
            list = bizReportService.selectWebReportList(bizReport, sysRoles);
        }

//        logger.info(list.toString());
//        for (BizReportVo tmp : list) {
//            logger.info(tmp.getReportNo());
//            logger.info(tmp.getMiddlemanCompany());
//        }
        return getDataTable(list);
    }

    /**
     * 导出报备管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:report:export')")
    @Log(title = "报备管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BizReport bizReport)
    {
        // 判断当前登录用户是否渠道专员
        List<SysRole> sysRoles = roleService.selectRoleByUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        List<BizReportVo> list = bizReportService.selectWebReportList(bizReport, sysRoles);
        ExcelUtil<BizReportVo> util = new ExcelUtil<>(BizReportVo.class);
        return util.exportExcel(list, "报备信息");
    }

    /**
     * 导入报备管理列表
     */
    @Log(title = "报备管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('business:report:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<BizReport> util = new ExcelUtil<>(BizReport.class);
        List<BizReport> bizReportList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String message = bizReportService.importReports(bizReportList, loginUser.getUsername());
        return AjaxResult.success(message);
    }

    /**
     * 获取报备管理详细信息
     */
    @ApiOperation("获取报备管理详细信息")
    @PreAuthorize("@ss.hasPermi('business:report:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bizReportService.selectBizReportById(id));
    }

    /**
     * 新增报备管理
     */
    @ApiOperation("新增报备管理")
    @Log(title = "报备管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizReport bizReport)
    {
        BizUser bizUser = SecurityUtils.getLoginUser().getBizUser();
        if (StringUtils.isNull(bizUser)) {
            return AjaxResult.error("没有登录用户");
        }

        if (!bizUser.getUserRole().equals(BizUserRole.AGENT.getCode()) && !bizUser.getUserRole().equals(BizUserRole.RECEPTION.getCode()) ) {
            return AjaxResult.error("当前用户不是经纪人或者前台不能报备！");
        }

        //todo：客户有过报备记录，过期报备记录中介是悠然地产中介
        // 1、当下中介不是悠然地产中介，不可以报备
        // 2、当下中介是悠然地产中介，可以报备

        // 渠道专员编号
        Long channelSysUserId = bizReport.getChannelSysUserId();
        if (StringUtils.isNull(channelSysUserId)) {
            return AjaxResult.error("渠道专员编号不能为空");
        }

        Date appointmentTime = bizReport.getAppointmentTime(); //报备预约时间
        if (appointmentTime == null) {
            return AjaxResult.error("报备预约时间不能为空");
        }

        //成交房源防重
        checkPurchasedHouses(bizReport.getPurchasedHousesId());

        String appointmentTimeStr = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, appointmentTime);
        String now = DateUtils.getDate();
        String nextDay = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, DateTime.now().plusDays(1).toDate());
        if (!appointmentTimeStr.equals(now) && !appointmentTimeStr.equals(nextDay)) {
            return AjaxResult.error("非有效时间段到访，请重新报备");
        }
        Date nowDate = DateUtils.getNowDate();
        bizReport.setReportTime(nowDate); //报备时间
        // 如果存在意向楼盘编号并且客户手机号、客户备用手机号、客户备用手机号3这三个手机号有一个重复则报错
        List<BizReport> reportList = bizReportService.selectProtectReport(bizReport);
        if (!StringUtils.isEmpty(reportList)) {
            return AjaxResult.error("存在报备有效期保护，不能重复报备");
        }
        bizReport.setUserId(bizUser.getId());
        // 如果登录用户是经济人则经济人编号为登录用户编号
        if (bizUser.getUserRole().equals(BizUserRole.AGENT.getCode())) {
            bizReport.setMiddlemanUserId(bizUser.getId());
        }
        bizReport.setReportValidityTime(new DateTime(nowDate).plusDays(30).toDate()); //报备有效时间=报备时间+30天
        // 查询服务商编号
        Long serviceProviderId = deptService.selectServiceProviderId(channelSysUserId);
        bizReport.setServiceProviderId(serviceProviderId); // 服务商编号
        bizReport.setDeptId(serviceProviderId);
        return toAjax(bizReportService.insertBizReport(bizReport));
    }

    /**
     * 修改报备管理
     */
    @ApiOperation("修改报备管理")
    @PreAuthorize("@ss.hasPermi('business:report:edit')")
    @Log(title = "报备管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizReport bizReport)
    {
        if (StringUtils.isNull(bizReport)) {
            return AjaxResult.error("报备信息为空");
        }
        BizReport old = bizReportService.selectBizReportById(bizReport.getId());
        String reportStatus = old.getReportStatus();
        // 状态为已交定金、首付已交齐、已网签、已办理贷款、已全款、已交房、已办理房产证等状态的时候，不能修改报备相关信息仅能修改报备状态
        if (!StringUtils.isEmpty(reportStatus)) {
            if (reportStatus.equals(BizReportStatus.DEPOSIT_PAID.getCode()) ||
                    reportStatus.equals(BizReportStatus.DOWN_PAID.getCode()) ||
                    reportStatus.equals(BizReportStatus.ONLINE_SIGNED.getCode()) ||
                    reportStatus.equals(BizReportStatus.PROCESSED_LOANS.getCode()) ||
                    reportStatus.equals(BizReportStatus.FULL_PAID.getCode()) ||
                    reportStatus.equals(BizReportStatus.DELIVERED.getCode()) ||
                    reportStatus.equals(BizReportStatus.HOUSES_PROPERTY.getCode())) {
                boolean result = BeanUtils.checkObject(bizReport, Lists.newArrayList("reportStatus", "id"));
                if (result) {
                    return AjaxResult.error("状态为已交定金、首付已交齐、已网签、已办理贷款、已全款、已交房、已办理房产证等状态的时候，不能修改报备相关信息仅能修改报备状态");
                }
            }
        }
        // 如果存在意向楼盘编号并且客户手机号、客户备用手机号、客户备用手机号3这三个手机号有一个重复则报错
        List<BizReport> reportList = bizReportService.selectProtectReport(bizReport);
        if (!StringUtils.isEmpty(reportList)) {
            return AjaxResult.error("存在报备有效期保护，不能重复报备");
        }
        //成交房源防重
        checkPurchasedHouses(bizReport.getPurchasedHousesId());
        // 报备状态 6-交付完成 -> 报备有效性 2-有效
        if (!StringUtils.isEmpty(bizReport.getReportStatus()) && bizReport.getReportStatus().equals(BizReportStatus.HOUSES_PROPERTY.getCode())) {
            bizReport.setValidStatus(BizValidStatus.EFFECTIVE.getCode());
        }
        bizReport.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(bizReportService.editReport(bizReport));
    }

    /**
     * 成交房源防重
     * @param purchasedHousesId 成交房源编号
     */
    private void checkPurchasedHouses(Integer purchasedHousesId) {
        if (StringUtils.isNull(purchasedHousesId)) {
            return;
        }
        BizReport report = new BizReport();
        report.setPurchasedHousesId(purchasedHousesId);
        List<BizReport> bizReports = bizReportService.selectBizReportList(report);
        if (!StringUtils.isEmpty(bizReports)) {
            throw new CustomException("已存在相同的成交房源的报备！");
        }
    }

    /**
     * 删除报备管理
     */
    @PreAuthorize("@ss.hasPermi('business:report:remove')")
    @Log(title = "报备管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bizReportService.updateBizReportDelStatus(ids));
    }
}
