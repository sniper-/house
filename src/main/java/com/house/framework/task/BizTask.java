package com.house.framework.task;

import com.house.common.enums.BizValidStatus;
import com.house.common.utils.StringUtils;
import com.house.project.business.domain.BizReport;
import com.house.project.business.service.IBizReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("bizTask")
public class BizTask {

    private static final Logger logger = LoggerFactory.getLogger(BizTask.class);

    @Autowired
    private IBizReportService reportService;

    /**
     * 查询当前时间-报备时间 >30天的有效性为0-处理中的报备记录设置成1-无效
     */
    public void updateOvertimeReport()
    {
        logger.info("updateOvertimeReport job start...");
        // 查询到当前时间-报备时间 >30天的有效性为0-处理中的报备记录  自访自拓客户不修改
        List<BizReport> bizReports = reportService.selectOvertimeReport();
        if (StringUtils.isEmpty(bizReports)) {
            logger.info("没有查询到当前时间-报备时间 >30天的有效性为0-处理中的报备记录");
            return;
        }
        // 将报备有效性改为无效
        bizReports.forEach(report -> {
            report.setValidStatus(BizValidStatus.INVALID.getCode());
            reportService.updateBizReport(report);
        });
        logger.info("updateOvertimeReport job end...");
    }

    /**
     * 更新当前时间-报备时间 > 7天的自拓客户报备记录为失效
     * 自拓客户7天失效，报备时判断是否报备过
     */
    public void updateOutOfSelfTime()
    {
        logger.info("updateOutOfSelfTime job start...");
        reportService.updateOutOfSelfTime();
        logger.info("updateOutOfSelfTime job end...");
    }

    /**
     * 更新当前时间-报备时间 > 24小时并且未到访的报备记录
     */
    public void updateOutOfAppointmentTime()
    {
        logger.info("updateOutOfAppointmentTime job start...");
        reportService.updateOutOfAppointmentTime();
        logger.info("updateOutOfAppointmentTime job end...");
    }

}
