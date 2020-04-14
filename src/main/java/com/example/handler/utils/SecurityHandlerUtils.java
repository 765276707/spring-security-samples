package com.example.handler.utils;

import com.alibaba.fastjson.JSON;
import com.example.dto.ResponseResult;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SecurityHandlerUtils {

    /**
     * 对外响应
     * @param httpServletResponse
     * @param responseResult
     * @throws IOException
     */
    public static void response(HttpServletResponse httpServletResponse,
                                ResponseResult responseResult) throws IOException {
        // 构建响应数据
        String jsonResult = JSON.toJSONString(responseResult);
        // 设置响应类型
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("utf-8");
        // 输出
        PrintWriter pw = httpServletResponse.getWriter();
        pw.write(jsonResult);
        pw.flush();
    }

}
