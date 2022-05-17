package com.zeng.usercenter.service;

import com.zeng.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author asus
* @description 针对表【user】的数据库操作Service
* @createDate 2022-05-16 16:30:05
*/
public interface UserService extends IService<User> {
    //在接口中 方法默认是public
    long userRegistry(String userAccount,String userPassword,String checkPassword);
}
