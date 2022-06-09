package com.zeng.usercenter.controller;

import com.zeng.usercenter.common.BaseResponse;
import com.zeng.usercenter.common.ResultUtils;
import com.zeng.usercenter.model.domain.Admin;
import com.zeng.usercenter.service.AdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 祝英台炸油条
 * @Time : 2022/6/9 12:33
 **/

@RestController
@RequestMapping("/music")
public class MusicAdminManageController {
    @Resource
    private AdminService adminService;

    @GetMapping("/adminSearch")
    public BaseResponse<List<Admin>> musicAdminSearch(){
        return ResultUtils.success(adminService.list());
    }

    @GetMapping("/deleteAdmin")
    public BaseResponse<Admin>  deleteAdmin( @RequestParam(value="id") int id){
        adminService.removeById(id);
        return ResultUtils.success(null);
    }
}
