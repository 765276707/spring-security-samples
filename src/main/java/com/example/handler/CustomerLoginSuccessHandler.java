package com.example.handler;

import com.alibaba.fastjson.JSON;
import com.example.dto.ResponseResult;
import com.example.handler.utils.SecurityHandlerUtils;
import com.example.properties.JwtProperties;
import com.example.utils.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义登录成功响应
 * @author xzb
 */
@Component
public class CustomerLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        // JWT
        String token = JwtUtils.createToken(String.valueOf(authentication.getPrincipal()), JwtProperties.privateKey);
        // 构建响应数据，返回Token
        ResponseResult result = new ResponseResult(200, "登录成功", token);
        // 输出
        SecurityHandlerUtils.response(httpServletResponse, result);
    }

}
