package com.house.framework.config;

import com.house.framework.config.security.BizUserAuthenticationProvide;
import com.house.framework.config.security.SysUserAuthenticationProvider;
import com.house.framework.security.service.BizUserDetailsServiceImpl;
import com.house.framework.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.house.framework.security.filter.JwtAuthenticationTokenFilter;
import com.house.framework.security.handle.AuthenticationEntryPointImpl;
import com.house.framework.security.handle.LogoutSuccessHandlerImpl;


/**
 * spring security配置
 *
 * @author vls-house
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class  SecurityConfig extends WebSecurityConfigurerAdapter
{

    /**
     * 认证失败处理类
     */
    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;

    /**
     * 退出处理类
     */
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * token认证过滤器
     */
    @Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    @Bean
    UserDetailsService bizUserService() {
        return new BizUserDetailsServiceImpl();
    }

    @Bean
    UserDetailsService userService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    DaoAuthenticationProvider daoSysUserAuthenticationProvider(){
        return new SysUserAuthenticationProvider(bCryptPasswordEncoder(), userService());
    }

    @Bean
    DaoAuthenticationProvider daoBizUserAuthenticationProvider(){
        return new BizUserAuthenticationProvide(bCryptPasswordEncoder(), bizUserService());
    }

    /**
     * 解决 无法直接注入 AuthenticationManager
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     *
     * 如何不登录直接访问(匿名访问方法):
     * 1. 去除.anyRequest().authenticated()
     * 2. 添加.antMatchers("/admins/**").permitAll()
     * 3. 对应Controller方法上的@PreAuthorize权限注解也需要去掉。
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity
                // CRSF禁用，因为不使用session
                .csrf().disable()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 过滤请求
                .authorizeRequests()
                // 对于登录login 验证码captchaImage, captchaSms 允许匿名访问
                .antMatchers("/login", "/captchaImage", "/captchaSms").anonymous()
                .antMatchers("/business/user/login", // H5登录
                        "/business/user/resetPwd", // H5重置密码
                        "/business/user/register", // H5用户注册
                        "/business/project/list", // 查询楼盘管理列表
                        "/business/project/detail", // 查询楼盘详细
                        "/business/image/list", // 查询楼盘照片管理列表
                        "/business/ht/list", // 查询楼盘户型管理列表
                        "/business/organization/intermediary").anonymous() // 查询中介公司
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
//                        "/admins/**",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .antMatchers("/profile/**").anonymous()
                .antMatchers("/common/download**").anonymous()
                .antMatchers("/common/download/resource**").anonymous()
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                .antMatchers("/druid/**").anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
        httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
        // 添加JWT filter
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }


    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.authenticationProvider(daoSysUserAuthenticationProvider());
        auth.authenticationProvider(daoBizUserAuthenticationProvider());
    }
}
