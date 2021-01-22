package com.house.project.business.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.house.common.constant.Constants;
import com.house.common.enums.BizHousesStatus;
import com.house.common.enums.BizReportStatus;
import com.house.common.enums.BizUserRole;
import com.house.common.exception.CustomException;
import com.house.common.utils.DateUtils;
import com.house.common.utils.IdUtils;
import com.house.common.utils.SecurityUtils;
import com.house.common.utils.StringUtils;
import com.house.project.business.domain.BizDimissionDistribution;
import com.house.project.business.domain.BizHouses;
import com.house.project.business.domain.BizUser;
import com.house.project.business.domain.vo.BizHousesVo;
import com.house.project.business.domain.vo.BizReportVo;
import com.house.project.business.domain.vo.BizUserVo;
import com.house.project.business.mapper.BizHousesMapper;
import com.house.project.business.mapper.BizUserMapper;
import com.house.project.business.service.IBizDimissionDistributionService;
import com.house.project.system.domain.SysRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.project.business.mapper.BizReportMapper;
import com.house.project.business.domain.BizReport;
import com.house.project.business.service.IBizReportService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 报备管理Service业务层处理
 *
 * @author bo.zhang
 * @date 2020-07-16
 */
@Service
public class BizReportServiceImpl implements IBizReportService
{
    @Autowired
    private BizReportMapper bizReportMapper;
    @Autowired
    private BizUserMapper userMapper;
    @Autowired
    private BizHousesMapper housesMapper;

    @Autowired
    private IBizDimissionDistributionService dimissionDistributionService;

    private static final Logger log = LoggerFactory.getLogger(BizReportServiceImpl.class);

    /**
     * 更新当前时间-报备时间 > 24小时并且未到访的报备记录
     * @return 结果
     */
    @Override
    public int updateOutOfAppointmentTime() {
        return bizReportMapper.updateOutOfAppointmentTime();
    }

    /**
     * 更新当前时间-报备时间 > 7天的自拓客户报备记录为失效
     */
    @Override
    public int updateOutOfSelfTime() {
        return bizReportMapper.updateOutOfSelfTime();
    }

    /**
     * 根据部门号查询业务用户部分下的所有用户id
     * @param user 业务用户
     * @return 结果
     */
    @Override
    public Integer[] queryUserIdsByOrg(BizUser user) {
        // 店长、中介公司经理、房产公司经理查询所属公司业务用户id
        if (user.getUserRole().equals(BizUserRole.OWNER.getCode()) ||
                user.getUserRole().equals(BizUserRole.MEDIUM_MANAGER.getCode()) ||
                user.getUserRole().equals(BizUserRole.COMPANY_MANAGER.getCode())) {
            BizUser userParam = new BizUser();
            userParam.setOrganizationCode(user.getOrganizationCode());
            List<BizUserVo> userList = userMapper.selectBizUserList(userParam);
            if (!StringUtils.isEmpty(userList)) {
                List<Integer> userIds = userList.stream().map(BizUser::getId).collect(Collectors.toList());
                int len = userIds.size();
                return userIds.toArray(new Integer[len]);
            }
        }
        return null;
    }

    /**
     * 查询H5管理列表
     * @param report 报备查询条件
     * @return 结果
     */
    @Override
    public List<BizReportVo> selectH5ReportList(BizReport report, BizUser user) {
        String userRole = user.getUserRole();
        // 经纪人->经济人报备的所有预约记录
        if (userRole.equals(BizUserRole.AGENT.getCode())) {
            report.setMiddlemanUserId(user.getId());
            return bizReportMapper.selectReportDetail(report);
        }
        // 置业顾问->置业顾问处理的所有预约记录
        if (userRole.equals(BizUserRole.ADVISER.getCode())) {
            report.setConsultantUserId(user.getId());
            return bizReportMapper.selectReportDetail(report);
        }
        // 前台->所属楼盘的所有预约记录(意向楼盘编号)
        if (userRole.equals(BizUserRole.RECEPTION.getCode())) {
            if (StringUtils.isNull(user.getProjectId())) {
                throw new RuntimeException("前台所属楼盘编号为空");
            }
            report.setIntentionProjectId(user.getProjectId());
            return bizReportMapper.selectReportDetail(report);
        }
        // 店长、中介公司经理->所属中介公司的所有预约记录
        if (userRole.equals(BizUserRole.OWNER.getCode()) || userRole.equals(BizUserRole.MEDIUM_MANAGER.getCode())) {
            return bizReportMapper.selectByMiddlemanUsers(report);
        }
        // 房产公司经理->所属房产公司的所有预约记录
        if (userRole.equals(BizUserRole.COMPANY_MANAGER.getCode())) {
            return bizReportMapper.selectByConsultantUsers(report);
        }
        throw new RuntimeException("当前登录用户角色异常");
    }

    /**
     * 查询渠道专员报备管理列表
     * @param report 报备查询条件
     * @return 结果
     */
    @Override
    public List<BizReportVo> selectWebReportList(BizReport report, List<SysRole> sysRoles) {
        if (StringUtils.isEmpty(sysRoles)) {
            return bizReportMapper.selectReportDetail(report);
        }
        // 如果当前登录用户不是渠道专员
        Optional<SysRole> optional = sysRoles.stream().filter(role -> role.getRoleKey().equals(Constants.CHANNEL_SYS_USER)).findAny();
        if (!optional.isPresent()) {
            return bizReportMapper.selectReportDetail(report);
        }
        // 查询渠道专员的报备信息
        report.setChannelSysUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        return bizReportMapper.selectReportDetail(report);
    }

    @Override
    public List<BizReport> selectOvertimeReport() {
        //biz_user.user_role!=4(自访) biz_user.organization_code!=悠然机构编码(自拓)
        return bizReportMapper.selectOvertimeReport();
    }

    @Override
    public BizReport selectBizReportByReportNo(String reportNo) {
        return bizReportMapper.selectBizReportByReportNo(reportNo);
    }

    /**
     * 查询报备有效期保护
     * @param report 参数 customerPhone intentionProjectId
     * @return 结果
     */
    @Override
    public List<BizReport> selectProtectReport(BizReport report) {
        if (StringUtils.isEmpty(report.getCustomerPhone()) || StringUtils.isNull(report.getIntentionProjectId())) {
            return null;
        }
        return bizReportMapper.selectProtectReport(report);
    }

    /**
     * 查询对应置业顾问的报备信息
     * @param consultantId 置业顾问编号
     * @return 结果
     */
    public List<BizReportVo> selectByConsultantUserId(Integer consultantId) {
        return bizReportMapper.selectByConsultantUserId(consultantId);
    }

    /**
     * 置业顾问离职重新分配报备信息, 修改置业顾问id
     * @param list 离职分配列表
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int distribution(List<BizDimissionDistribution> list) {
        //批量新增离职分配记录
        String uuid = IdUtils.randomUUID();
        list.forEach(d -> {
            d.setDistributionBatch(uuid);
            d.setDistributionTime(DateUtils.getNowDate());
            dimissionDistributionService.insertBizDimissionDistribution(d);
            bizReportMapper.updateBizReportConsultant(d.getNewConsultantUserId(), d.getOriginalRconsultantUserId(), d.getReportNo());
        });
        //更新置业顾问
        return list.size();
    }

    /**
     * 批量将报备管理停用
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int updateBizReportDelStatus(Integer[] ids) {
        return bizReportMapper.updateBizReportDelStatus(ids);
    }

    /**
     * 查询报备管理
     *
     * @param id 报备管理ID
     * @return 报备管理
     */
    @Override
    public BizReportVo selectBizReportById(Integer id)
    {
        return bizReportMapper.selectBizReportById(id);
    }

    /**
     * 查询报备管理列表
     *
     * @param bizReport 报备管理
     * @return 报备管理
     */
    @Override
    public List<BizReport> selectBizReportList(BizReport bizReport)
    {
        return bizReportMapper.selectBizReportList(bizReport);
    }

    /**
     * 新增报备管理
     *
     * @param bizReport 报备管理
     * @return 结果
     */
    @Override
    public int insertBizReport(BizReport bizReport)
    {
        bizReport.setCreateBy(SecurityUtils.getUsername());
        bizReport.setCreateTime(DateUtils.getNowDate());
        return bizReportMapper.insertBizReport(bizReport);
    }

    /**
     * 更新报备管理
     *
     * @param bizReport 报备管理
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int editReport(BizReport bizReport) {
        String reportStatus = bizReport.getReportStatus() == null ? "" : bizReport.getReportStatus();
        Integer purchasedHousesId = bizReport.getPurchasedHousesId(); //成交房源编号
        // 变更状态为已交定金的时候修改房源信息为已售。修改报备信息的时候判断成交房源是否已售，已售房源报错，该房源已经出售不能报备
        if (reportStatus.equals(BizReportStatus.DEPOSIT_PAID.getCode()) && purchasedHousesId != null) {
            BizHousesVo bizHousesVo = housesMapper.selectBizHousesById(purchasedHousesId);
            if (bizHousesVo.getHousesStatus().equals(BizHousesStatus.SOLD.getCode())) {
                throw new CustomException("该房源已经出售不能报备！");
            }
            BizHouses houses = new BizHouses();
            houses.setId(bizHousesVo.getId());
            houses.setHousesStatus(BizHousesStatus.SOLD.getCode());
            housesMapper.updateBizHouses(houses);
        }
        // 更新报备信息
        return updateBizReport(bizReport);
    }

    /**
     * 修改报备管理
     *
     * @param bizReport 报备管理
     * @return 结果
     */
    @Override
    public int updateBizReport(BizReport bizReport)
    {
        bizReport.setUpdateTime(DateUtils.getNowDate());
        return bizReportMapper.updateBizReport(bizReport);
    }

    /**
     * 批量删除报备管理
     *
     * @param ids 需要删除的报备管理ID
     * @return 结果
     */
    @Override
    public int deleteBizReportByIds(Integer[] ids)
    {
        return bizReportMapper.deleteBizReportByIds(ids);
    }

    /**
     * 删除报备管理信息
     *
     * @param id 报备管理ID
     * @return 结果
     */
    @Override
    public int deleteBizReportById(Integer id)
    {
        return bizReportMapper.deleteBizReportById(id);
    }

    @Override
    public String importReports(List<BizReport> reportList, String createBy){
        if (StringUtils.isNull(reportList) || reportList.size() == 0)
        {
            throw new CustomException("导入数据不能为空！");
        }
        if (StringUtils.isEmpty(createBy)) {
            throw new CustomException("操作用户不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String housesName = null;
        for (BizReport report : reportList)
        {
            try
            {
                if (StringUtils.isNull(report.getReportNo())) {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、报备编号为空");
                    continue;
                }
                if (StringUtils.isNull(report.getUserId())) {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、用户ID为空");
                    continue;
                }
                if (StringUtils.isEmpty(report.getCustomerName())) {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、手机号为空");
                    continue;
                }
                if (StringUtils.isEmpty(report.getDeptId().toString())) {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、服务商编号为空");
                    continue;
                }
                if (StringUtils.isEmpty(report.getReportTime().toString())) {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、报备时间为空");
                    continue;
                }
                if (StringUtils.isEmpty(report.getReportStatus())) {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、报备状态为空");
                    continue;
                }
                if (StringUtils.isEmpty(report.getValidStatus())) {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、报备有效性为空");
                    continue;
                }


                report.setCreateBy(createBy);
                insertBizReport(report);
                successNum++;
                successMsg.append("<br/>").append(successNum).append("、房源 ").append(housesName).append(" 导入成功");
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、报备信息 " + housesName + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
