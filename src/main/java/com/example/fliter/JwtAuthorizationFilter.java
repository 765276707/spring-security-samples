package com.example.fliter;

import com.example.fliter.service.SelfUserDetailsService;
import com.example.handler.CustomerAuthenticationEntryPoint;
import com.example.properties.JwtProperties;
import com.example.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义的JWT认证过滤器
 * @author xzb
 */
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private SelfUserDetailsService selfUserDetailsService;
    @Autowired
    private CustomerAuthenticationEntryPoint entryPoint;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 从Header中获取令牌
        String token = httpServletRequest.getHeader("Authorization");
        if (!StringUtils.isEmpty(token)) {

            // 校验令牌的合法性
            String username = null;
            try {
                username = JwtUtils.getUsernameFromJwt(token, JwtProperties.publicKey);
            } catch (Exception e) {
                e.printStackTrace();
                entryPoint.commence(httpServletRequest, httpServletResponse, null);
                return;
            }

            boolean existAuthentication = SecurityContextHolder.getContext().getAuthentication()!=null;
            if (null!=username && !existAuthentication) {
                // 当前上下文中未存在该用户的认证信息
                UserDetails jwtUser =  selfUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        jwtUser.getUsername(), jwtUser.getPassword(), jwtUser.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                // 重新保存认证信息
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } else {
            // 未携带令牌
            entryPoint.commence(httpServletRequest, httpServletResponse, null);
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
