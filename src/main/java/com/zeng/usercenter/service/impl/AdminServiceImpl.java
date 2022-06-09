package com.zeng.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeng.usercenter.model.domain.Admin;
import com.zeng.usercenter.service.AdminService;
import com.zeng.usercenter.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
* @author asus
* @description 针对表【admin】的数据库操作Service实现
* @createDate 2022-06-09 12:31:00
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

}




