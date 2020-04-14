package com.example.handler;

import com.example.dto.ResponseResult;
import com.example.handler.utils.SecurityHandlerUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录失败响应
 * @author xzb
 */
@Component
public class CustomerLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        // 构建响应数据
        ResponseResult result = new ResponseResult(100, "账号或密码错误", "");
        // 输出
        SecurityHandlerUtils.response(httpServletResponse, result);
    }

}
