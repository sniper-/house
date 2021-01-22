package com.house.framework.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * BizUser专用的BizUserAuthenticationToken
 * AuthenticationManager会遍历使用Provider的supports()方法,判断AuthenticationToken是不是自己想要的
 */
public class BizUserAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public BizUserAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
