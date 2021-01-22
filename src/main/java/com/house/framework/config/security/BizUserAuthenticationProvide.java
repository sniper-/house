package com.house.framework.config.security;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * BizUser专用的AuthenticationProvider
 * 选择实现DaoAuthenticationProvider是因为比较方便且能用
 */
public class BizUserAuthenticationProvide extends DaoAuthenticationProvider {

    /**
     * 初始化 将使用Manager专用的userDetailsService
     */
    public BizUserAuthenticationProvide(PasswordEncoder encoder, UserDetailsService userDetailsService){
        setPasswordEncoder(encoder);
        setUserDetailsService(userDetailsService);
    }

    @Override
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        super.setPasswordEncoder(passwordEncoder);
    }

    @Override
    public void setUserDetailsPasswordService(UserDetailsPasswordService userDetailsPasswordService) {
        super.setUserDetailsPasswordService(userDetailsPasswordService);
    }

    /**
     * 判断只有传入BizUserAuthenticationToken的时候才使用这个Provider
     * supports会在AuthenticationManager层被调用
     */
    public boolean supports(Class<?> authentication) {
        return BizUserAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
