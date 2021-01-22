package com.house.framework.security.service;

import com.google.common.collect.Sets;
import com.house.common.constant.Constants;
import com.house.common.enums.UserStatus;
import com.house.common.exception.BaseException;
import com.house.common.utils.DateUtils;
import com.house.common.utils.StringUtils;
import com.house.framework.security.LoginUser;
import com.house.project.business.domain.BizUser;
import com.house.project.business.service.IBizUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;

public class BizUserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(BizUserDetailsServiceImpl.class);

    @Autowired
    private IBizUserService bizUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        BizUser user = bizUserService.selectBizUserByName(username);
        if (StringUtils.isNull(user))
        {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
        else if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            log.info("登录用户：{} 已被停用.", username);
            throw new BaseException("对不起，您的账号：" + username + " 已停用");
        }
        else if (user.getWrongPasswordTimes() != null && user.getWrongPasswordTimes() >= Constants.PASSWORD_MAX_WRONG_TIMES)
        {
            log.info("登录用户：{} 已被停用.", username);
            throw new BaseException("对不起，您达到最大失败次数：" + username + " 请在一天后重试");
        }

        // 给H5用户赋管理员用户权限
        return new LoginUser(user, Sets.newHashSet("*:*:*"));
    }

}
