package com.zeng.usercenter.service;

import com.zeng.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import javax.servlet.http.HttpServletRequest;


/**
* @author asus
* @description 针对表【user】的数据库操作Service
* @createDate 2022-05-16 16:30:05
*/
public interface UserService extends IService<User> {
    //用户注册
    long userRegistry(String userAccount,String userPassword,String checkPassword,String planetCode);
    //用户登录
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);
    //用户脱敏
    User getSafeUser(User user);
    //删除用户，删除用户态
    Integer userLogout(HttpServletRequest request);
}
