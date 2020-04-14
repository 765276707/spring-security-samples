package com.example.config;

import com.example.fliter.JwtAuthorizationFilter;
import com.example.fliter.service.SelfUserDetailsService;
import com.example.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomerLoginSuccessHandler loginSuccessHandler;
    @Autowired
    private CustomerLoginFailureHandler loginFailureHandler;
    @Autowired
    private CustomerLogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private CustomerAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private CustomerAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private SelfUserDetailsService userDetailsService;
    @Autowired
    private JwtAuthorizationFilter jwtAuthenticationFilter;


    // auth 配置
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("xzb")
//                .password(passwordEncoder().encode("xzb"))
//                .authorities("get_data");
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }



    // http 配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 认证相关
        http.authorizeRequests()
                .anyRequest().authenticated();

        // 禁用CSRF 和 Session
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 过滤器配置
        http.addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 无权限、未登录配置
        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);

        // 配置登录（登陆相关不需要验证权限）
        http.formLogin()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .permitAll();

        // 配置登出
        http.logout()
                .logoutSuccessHandler(logoutSuccessHandler);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
