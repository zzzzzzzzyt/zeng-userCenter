package com.zeng.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeng.usercenter.service.UserService;
import com.zeng.usercenter.model.domain.User;
import com.zeng.usercenter.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author asus
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-05-16 15:15:23
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}




