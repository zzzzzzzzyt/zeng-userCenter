package com.zeng.usercenter.service;

import com.zeng.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class UserServiceTest {


    @Autowired
    public UserService userService;
    @Test
    void test()
    {
        User user = new User();
        user.setUserName("sas");
        user.setUserAccount("sasa");
        user.setAvatarUrl("sasad");
        user.setGender(0);
        user.setUserPassword("dsad");
        user.setPhone("asdsa");
        user.setEmail("asdsad");
        user.setUserStatus(0);
        user.setIsDelete(0);
        boolean result = userService.save(user);
        Assertions.assertTrue(result);
        System.out.println();
    }
}