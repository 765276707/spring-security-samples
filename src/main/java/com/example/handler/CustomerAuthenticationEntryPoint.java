package com.example.handler;

import com.example.dto.ResponseResult;
import com.example.handler.utils.SecurityHandlerUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义未登录响应
 * @author xzb
 */
@Component
public class CustomerAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        // 构建响应数据
        ResponseResult result = new ResponseResult(401, "您未登录", "");
        // 输出
        SecurityHandlerUtils.response(httpServletResponse, result);
    }

}
