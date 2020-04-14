package com.example.handler;

import com.example.dto.ResponseResult;
import com.example.handler.utils.SecurityHandlerUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义无访问权限响应
 * @author xzb
 */
@Component
public class CustomerAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        // 构建响应数据
        ResponseResult result = new ResponseResult(403, "无访问权限", "");
        // 输出
        SecurityHandlerUtils.response(httpServletResponse, result);
    }

}
