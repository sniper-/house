package com.house.framework.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * SysUser专用的SysUserAuthenticationToken
 * AuthenticationManager会遍历使用Provider的supports()方法,判断AuthenticationToken是不是自己想要的
 */
public class SysUserAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public SysUserAuthenticationToken(Object principal, Object credentials){
        super(principal,credentials);
    }
}
