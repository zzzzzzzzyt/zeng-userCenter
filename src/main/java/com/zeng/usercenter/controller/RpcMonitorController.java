package com.zeng.usercenter.controller;


import com.zeng.usercenter.common.BaseResponse;
import com.zeng.usercenter.common.ResultUtils;
import com.zeng.usercenter.model.domain.RpcMonitor;
import com.zeng.usercenter.service.RpcMonitorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Author 祝英台炸油条
 * @Time : 2022/6/3 14:12
 **/
@RestController
@RequestMapping("/method")
public class RpcMonitorController {

    @Resource
    private RpcMonitorService rpcMonitorService;

    @GetMapping("/search")
    public BaseResponse<List<RpcMonitor>> methodSearch() {
        return ResultUtils.success(rpcMonitorService.list());
    }
}
