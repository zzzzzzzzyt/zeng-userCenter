package com.zeng.usercenter.common;

/**
 * @Author 祝英台炸油条
 * @Time : 2022/5/19 16:56
 *
 * 枚举类 相应的变量
 **/
public enum ErrorCode {

    //进行枚举值的书写
    PARAM_ERROR(40000,"请求参数错误",""),
    NULL_ERROR(40001,"请求数据为空",""),
    NOT_LOGIN(40100,"未登录",""),
    NO_AUTH(40101,"无权限",""),
    SYSTEM_ERROR(50000,"系统内部异常","");

    private final int code;

    private final String message;

    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
