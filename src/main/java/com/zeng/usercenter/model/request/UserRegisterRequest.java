package com.zeng.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册时传来的请求类
 * @Author 祝英台炸油条
 * @Date 2022 05 17 15 05
 **/
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = -4802984224860111952L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String planetCode;
}
