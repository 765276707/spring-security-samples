package com.example.controller;

import com.example.dto.ResponseResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @PreAuthorize("hasAuthority('get_data')")
    @GetMapping("/get_data")
    public ResponseResult getData() {
        return new ResponseResult(200, "查询成功", "GET:这是您的数据");
    }

    @PostMapping("/post_data")
    public ResponseResult postData() {
        return new ResponseResult(200, "查询成功", "POST:这是您的数据");
    }
}
