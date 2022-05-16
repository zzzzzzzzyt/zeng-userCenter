package com.zeng.usercenter.service;

import com.zeng.usercenter.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

//进行简单的测试
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void insertTest()
    {
        User user = new User();
        user.setUserName("dsa");
        user.setUserAccount("dsd");
        user.setAvatarUrl("dsad");
        user.setGender(0);
        user.setUserPassword("sada");
        user.setPhone("sa");
        user.setEmail("aa");
        user.setUserStatus(0);
        user.setIsDelete(0);
        boolean result = userService.save(user);
        assertTrue(result);
    }
}