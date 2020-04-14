package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 对外统一响应格式
 * @author xzb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult {

    private Integer code;
    private String message;
    private Object data;

}
