package com.house.project.business.service.impl;

import java.util.Date;
import java.util.List;

import com.house.common.constant.Constants;
import com.house.common.constant.HttpStatus;
import com.house.common.enums.BizUserStatus;
import com.house.common.exception.CustomException;
import com.house.common.exception.user.UserPasswordTimesOverException;
import com.house.common.exception.user.UserPasswordWrongTimesException;
import com.house.common.utils.DateUtils;
import com.house.common.utils.SecurityUtils;
import com.house.common.utils.StringUtils;
import com.house.framework.config.security.BizUserAuthenticationToken;
import com.house.framework.redis.RedisCache;
import com.house.framework.security.LoginUser;
import com.house.framework.security.service.TokenService;
import com.house.framework.web.domain.AjaxResult;
import com.house.project.business.domain.vo.BizUserVo;
import com.house.project.business.domain.vo.RegisterVo;
import com.house.project.business.domain.vo.SuperiorsVo;
import com.house.project.system.service.ISysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.house.project.business.mapper.BizUserMapper;
import com.house.project.business.domain.BizUser;
import com.house.project.business.service.IBizUserService;

import javax.annotation.Resource;

/**
 * 业务用户Service业务层处理
 * 
 * @author bo.zhang
 * @date 2020-07-16
 */
@Service
public class BizUserServiceImpl implements IBizUserService 
{

    private static final Logger log = LoggerFactory.getLogger(BizUserServiceImpl.class);

    @Autowired
    private BizUserMapper bizUserMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private RedisCache redisCache;

    @Override
    public int updateBizUserDelStatus(Integer[] ids) {
        return bizUserMapper.updateBizUserDelStatus(ids);
    }


    @Resource
    private AuthenticationManager authenticationManager;

    /**
     * 短信和图片验证码验证
     * @param register 验证码信息
     * @return 结果
     */
    @Override
    public AjaxResult verifyResult(RegisterVo register) {
        if (StringUtils.isNull(register) || StringUtils.isNull(register.getUser())) {
            return AjaxResult.error("用户信息为空");
        }
        if (StringUtils.isNull(register.getSmsVerify()) ||
                StringUtils.isEmpty(register.getSmsVerify().getUuid()) ||
                StringUtils.isEmpty(register.getSmsVerify().getCode())) {
            return AjaxResult.error("短信验证码信息为空");
        }
        // 短信验证
        try {
            redisCache.verifyCode(register.getSmsVerify().getUuid(), register.getSmsVerify().getCode(), false);
        } catch (Exception e) {
            return new AjaxResult(HttpStatus.SMS_ERROR, e.getMessage(), null);
        }
        // 图形验证码不为空则验证图形验证码
        if (!StringUtils.isNull(register.getImgVerify()) &&
                !StringUtils.isEmpty(register.getImgVerify().getUuid()) &&
                !StringUtils.isEmpty(register.getImgVerify().getCode())) {
            redisCache.verifyCode(register.getImgVerify().getUuid(), register.getImgVerify().getCode(), true);
        }
        return null;
    }

    /**
     * 业务用户登录验证
     *
     * @param loginName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public String loginByBizUser(String loginName, String password)
    {
        // 用户验证
        Authentication authentication;

        BizUser user = selectBizUserByName(loginName);
        if (StringUtils.isNull(user)) {
            throw new UsernameNotFoundException("登录用户：" + loginName + " 不存在");
        }
        // 更新登录时间, 如果是隔天则重置失败次数
        long diffDays = 0;
        if (user.getLastLoginTime() != null) {
            diffDays = DateUtils.getDiffDays(new Date(), user.getLastLoginTime());
        }
        if (user.getWrongPasswordTimes() > Constants.PASSWORD_MIN_WRONG_TIMES && diffDays >= 1) {
            user.setWrongPasswordTimes(Constants.PASSWORD_MIN_WRONG_TIMES);
        }
        // 更新上次的登陆时间
        user.setLastLoginTime(DateUtils.getNowDate());
        updateBizUser(user);
        try {
            authentication = authenticationManager
                    .authenticate(new BizUserAuthenticationToken(loginName, password));
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                // 更新失败次数
                bizUserMapper.updateWrongPwdTimes(user);
                Integer residueDegree = Constants.PASSWORD_MAX_WRONG_TIMES - user.getWrongPasswordTimes() - 1;
                if (residueDegree == Constants.PASSWORD_MIN_WRONG_TIMES) {
                    throw new UserPasswordTimesOverException();
                } else {
                    throw new UserPasswordWrongTimesException(new Integer[]{residueDegree});
                }

            }
            else
            {
                throw new CustomException(e.getMessage());
            }
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 注册用户
     * @param register 注册信息
     * @param login 是否登录
     * @return 结果
     */
    @Override
    public AjaxResult registerUser(RegisterVo register, boolean login) {
        BizUser bizUser = register.getUser();
        bizUser = buildUser(bizUser, login);
        AjaxResult ajaxResult = verifyResult(register);
        if (!StringUtils.isNull(ajaxResult)) {
            return ajaxResult;
        }
        return insertBizUser(bizUser) > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 新增用户
     * @param bizUser 用户信息
     * @param login 是否登录
     * @return 结果
     */
    @Override
    public int addUser(BizUser bizUser, boolean login) {
        return insertBizUser(buildUser(bizUser, login));
    }

    /**
     * 构建新增业务用户信息
     * @param bizUser 业务用户
     * @param login 是否登录
     * @return 结果
     */
    private BizUser buildUser(BizUser bizUser, boolean login) {
        BizUser checkName = selectBizUserByName(bizUser.getLoginName());
        // 登录名验证
        if (!StringUtils.isNull(checkName))
        {
            throw new RuntimeException("新增业务用户'" + checkName.getLoginName() + "'失败，登录账号已存在");
        }
        // 手机号验证
        if (!StringUtils.isEmpty(bizUser.getPhone()))
        {
            BizUser checkPhone = selectBizUserByPhone(bizUser.getPhone());
            if (!StringUtils.isNull(checkPhone)) {
                throw new RuntimeException("新增业务用户'" + checkPhone.getLoginName() + "'失败，手机号码已存在");
            }
        }
        bizUser.setWrongPasswordTimes(Constants.PASSWORD_MIN_WRONG_TIMES);
        bizUser.setUserStatus(BizUserStatus.JOB.getCode()); // 默认在职
        bizUser.setCreateBy(login ? SecurityUtils.getUsername() : bizUser.getLoginName());
        if (!StringUtils.isEmpty(bizUser.getPassword())) {
            bizUser.setPassword(SecurityUtils.encryptPassword(bizUser.getPassword()));
        }
        return bizUser;
    }

    @Override
    public List<BizUser> selectSuperiors(SuperiorsVo superiors) {
        return bizUserMapper.selectSuperiors(superiors);
    }

    /**
     * 登录号查询业务用户
     * @param loginName 登录号
     * @return 业务用户
     */
    @Override
    public BizUser selectBizUserByName(String loginName) {
        return bizUserMapper.selectBizUserByName(loginName);
    }

    /**
     * 手机号查询业务用户
     * @param phone 手机号
     * @return 业务用户
     */
    @Override
    public BizUser selectBizUserByPhone(String phone) {
        return bizUserMapper.selectBizUserByPhone(phone);
    }

    /**
     * 查询业务用户
     * 
     * @param id 业务用户ID
     * @return 业务用户
     */
    @Override
    public BizUserVo selectBizUserById(Integer id)
    {
        return bizUserMapper.selectBizUserById(id);
    }

    /**
     * 查询业务用户列表
     * 
     * @param bizUser 业务用户
     * @return 业务用户
     */
    @Override
    public List<BizUserVo> selectBizUserList(BizUser bizUser)
    {
        return bizUserMapper.selectBizUserList(bizUser);
    }

    /**
     * 新增业务用户
     * 
     * @param bizUser 业务用户
     * @return 结果
     */
    @Override
    public int insertBizUser(BizUser bizUser)
    {
        bizUser.setCreateTime(DateUtils.getNowDate());
        bizUser.setWrongPasswordTimes(Constants.PASSWORD_MIN_WRONG_TIMES);
        return bizUserMapper.insertBizUser(bizUser);
    }

    /**
     * 修改业务用户
     * 
     * @param bizUser 业务用户
     * @return 结果
     */
    @Override
    public int updateBizUser(BizUser bizUser)
    {
        bizUser.setUpdateTime(DateUtils.getNowDate());
        return bizUserMapper.updateBizUser(bizUser);
    }

    /**
     * 批量删除业务用户
     * 
     * @param ids 需要删除的业务用户ID
     * @return 结果
     */
    @Override
    public int deleteBizUserByIds(Integer[] ids)
    {
        return bizUserMapper.deleteBizUserByIds(ids);
    }

    /**
     * 删除业务用户信息
     * 
     * @param id 业务用户ID
     * @return 结果
     */
    @Override
    public int deleteBizUserById(Integer id)
    {
        return bizUserMapper.deleteBizUserById(id);
    }

    /**
     * 导入业务用户数据
     *
     * @param userList 业务用户数据列表
     * @param createBy 操作用户
     * @return 结果
     */
    @Override
    public String importBizUser(List<BizUser> userList, String createBy)
    {
        if (StringUtils.isNull(userList) || userList.size() == 0)
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
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (BizUser user : userList)
        {
            try
            {
                if (StringUtils.isEmpty(user.getLoginName())) {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、用户登录号为空");
                    continue;
                }
                // 验证是否存在这个用户
                BizUser u = bizUserMapper.selectBizUserByName(user.getLoginName());
                if (StringUtils.isNull(u))
                {
                    user.setPassword(SecurityUtils.encryptPassword(password));
                    user.setCreateBy(createBy);
                    user.setWrongPasswordTimes(Constants.PASSWORD_MIN_WRONG_TIMES);
                    insertBizUser(user);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、账号 ").append(user.getUserName()).append(" 导入成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、账号 ").append(user.getLoginName()).append(" 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
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
