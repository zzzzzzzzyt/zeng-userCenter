package com.zeng.usercenter.common;

/**
 * @Author 祝英台炸油条
 * @Time : 2022/5/19 16:17
 *
 * 返回工具类
 **/
public class ResultUtils {
    public static <T> BaseResponse<T> success(T data)
    {
        return new BaseResponse<>(0,data,"ok");
    }

    public static <T> BaseResponse<T> fail(T data)
    {
        return new BaseResponse<>(0,data,"ok");
    }
}
