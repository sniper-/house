package com.house.framework.security.filter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.house.common.utils.SecurityUtils;
import com.house.common.utils.StringUtils;
import com.house.framework.security.LoginUser;
import com.house.framework.security.service.TokenService;

/**
 * token过滤器 验证token有效性
 * 
 * @author vls-house
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter
{
    @Autowired
    private TokenService tokenService;

    /** 不验证登录信息白名单 */
    private static List<String> IGNORE_URI = Lists.newArrayList("/business/project/list",
            "/business/project/detail",
            "/business/image/list",
            "/business/ht/list",
            "/business/organization/intermediary");


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException
    {
        String requestURI = request.getRequestURI();
        if (!StringUtils.isEmpty(requestURI)) {
            Optional<String> optional = IGNORE_URI.stream().filter(requestURI::contains).findAny();
            if (optional.isPresent()) {
                chain.doFilter(request, response);
                return;
            }
        }
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(SecurityUtils.getAuthentication()))
        {
            tokenService.verifyToken(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }
}
