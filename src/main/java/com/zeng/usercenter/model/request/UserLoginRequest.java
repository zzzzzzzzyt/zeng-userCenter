package com.zeng.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录时候传来的请求类
 * @Author 祝英台炸油条
 * @Date 2022 05 17 15 06
 **/
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 4673186504184402515L;
    private String userAccount;
    private String userPassword;
}
