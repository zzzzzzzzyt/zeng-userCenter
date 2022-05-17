package com.zeng.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeng.usercenter.model.domain.User;
import com.zeng.usercenter.service.UserService;
import com.zeng.usercenter.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

/**
* @author asus
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-05-16 16:30:05
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    /**
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 重复输入的密码
     * @return 返回用户id
     */
    @Override
    public long userRegistry(String userAccount, String userPassword, String checkPassword) {
        //引入方法进行校验 对应的字段们是否为空
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)) return -1;
        //用户的账户长度不能小于4位
        if (userAccount.length()<4)return -1;
        //

    }
}




