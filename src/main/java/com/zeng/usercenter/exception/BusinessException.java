package com.zeng.usercenter.exception;

import com.zeng.usercenter.common.ErrorCode;

/**
 * @Author 祝英台炸油条
 * @Time : 2022/5/19 18:23
 * <p>
 * 定义全局异常类
 * 1.相对于java的异常类，支持更多字段
 * 2.自定义构造函数，更灵活/快捷的设置字段
 **/
public class BusinessException extends RuntimeException {

    private final int code;

    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
