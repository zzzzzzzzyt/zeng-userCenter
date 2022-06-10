package com.zeng.usercenter.exception;

import com.zeng.usercenter.common.BaseResponse;
import com.zeng.usercenter.common.ErrorCode;
import com.zeng.usercenter.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author 祝英台炸油条
 * @Time : 2022/5/19 19:14
 * 全局异常处理类
 * 因为是rest所以返回的是Json对象  同时可以进行统一的日志处理
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 自定义异常处理类
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse BusinessExceptionHandler(BusinessException e) {
        log.error("businessException:" + e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    /**
     * 系统运行时异常处理类
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse RuntimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
    }


}
