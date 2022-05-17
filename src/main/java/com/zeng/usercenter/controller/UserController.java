package com.zeng.usercenter.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户接口 前端请求获取相应的json或者返回值  在前端上进行显示
 * @Author 祝英台炸油条
 * @Date 2022 05 17 14 48
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/register")
    public long register()
    {
        //todo 等等
    }


}
