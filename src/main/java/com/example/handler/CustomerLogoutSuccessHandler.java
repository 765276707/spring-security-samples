package com.example.handler;

import com.example.dto.ResponseResult;
import com.example.handler.utils.SecurityHandlerUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登出成功响应
 * @author xzb
 */
@Component
public class CustomerLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        // 构建响应数据
        ResponseResult result = new ResponseResult(200, "登出成功", "");
        // 输出
        SecurityHandlerUtils.response(httpServletResponse, result);
    }

}
