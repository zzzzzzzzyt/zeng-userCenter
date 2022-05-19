package com.zeng.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zeng.usercenter.model.domain.User;
import com.zeng.usercenter.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;

@SpringBootTest
class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
    void saveTest()
    {
        String userAccount = "asc";
        String userPassword = "dsadsadsad";
        String checkPassword = "dsadsadsad";
        String planetCode = "1";
        Assertions.assertEquals(userService.userRegistry(userAccount,userPassword,checkPassword,planetCode),-1);
        userAccount = "zeng";
        userPassword = "zeng";
        checkPassword  = "zeng";
        Assertions.assertEquals(userService.userRegistry(userAccount,userPassword,checkPassword,planetCode),-1);
        userAccount = "sasa";
        userPassword = "zengzeng";
        checkPassword  = "zengzeng";
        Assertions.assertEquals(userService.userRegistry(userAccount,userPassword,checkPassword,planetCode),-1);
        userAccount = "ze./¡£¡¢ng";
        userPassword = "zengzeng";
        checkPassword  = "zengzeng";
        Assertions.assertEquals(userService.userRegistry(userAccount,userPassword,checkPassword,planetCode),-1);
        userAccount = "zeng";
        userPassword = "zengzeng";
        checkPassword  = "zengzdsng";
        Assertions.assertEquals(userService.userRegistry(userAccount,userPassword,checkPassword,planetCode),-1);
        userAccount = "zytzytz";
        userPassword = "zengzeng";
        checkPassword  = "zengzeng";
        Assertions.assertEquals(userService.userRegistry(userAccount,userPassword,checkPassword,planetCode),-1);
    }


    @Test
    void testDelete()
    {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",1);
        Assertions.assertTrue(userService.remove(queryWrapper));
    }

}