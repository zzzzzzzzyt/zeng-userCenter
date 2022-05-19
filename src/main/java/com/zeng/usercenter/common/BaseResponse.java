package com.zeng.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author 祝英台炸油条
 * @Time : 2022/5/19 16:17
 *
 * 设置序列化的目的 就是为了完成相应的 可以完成序列化的传输
 **/
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this(code,data,"");
    }


}
