package com.zeng.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zeng.usercenter.model.domain.User;
import com.zeng.usercenter.model.request.UserLoginRequest;
import com.zeng.usercenter.model.request.UserRegisterRequest;
import com.zeng.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.zeng.usercenter.constants.UserConstant.ADMIN_ROLE;
import static com.zeng.usercenter.constants.UserConstant.USER_LOGIN_STATUS;

/**
 * 用户接口 前端请求获取相应的json或者返回值  在前端上进行显示
 * @Author 祝英台炸油条
 * @Date 2022 05 17 14 48
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public long registerUser(@RequestBody UserRegisterRequest userRegisterRequest)
    {
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        return userService.userRegistry(userAccount, userPassword, checkPassword);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public User loginUser(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request)
    {
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        //进行简单的测试
        if (StringUtils.isAnyBlank(userAccount,userPassword))return null;
        return userService.userLogin(userAccount, userPassword,request);
    }


    /**
     * 搜索对应用户
     */
    @GetMapping("/search")
    public List<User> searchUser(String username,HttpServletRequest request)
    {
        //搜索之前要进行鉴权 如果鉴权失败 则返回空
        if (!isAdmin(request))
        {
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(username))
        {
            queryWrapper.like("user_name",username);
        }
        List<User> list = userService.list(queryWrapper);
        return list.stream().map(user -> userService.getSafeUser(user)).collect(Collectors.toList());
    }


    /**
     * 获取当前用户信息
     * @param request 请求
     * @return 返回脱敏过后的用户对应信息
     */
    @GetMapping("/current")
    public User getCurrentUser(HttpServletRequest request)
    {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        if (userObj==null)return null;
        User currentUser = (User) userObj;
        //TODO 这边还是需要判断用户是否合法
        User user = userService.getById(currentUser.getId());
        return userService.getSafeUser(user);
    }


    /**
     * 删除对应用户
     */
    //TODO 这边有点问题 删除尚未实现  看看鱼皮怎么改 我是想直接换成get
    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody long id,HttpServletRequest request)
    {
        //同样需要鉴权
        if (!isAdmin(request)) return false;
        if (id<0)return false;
        return userService.removeById(id);
    }

    private boolean isAdmin(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Object userObj = session.getAttribute(USER_LOGIN_STATUS);
        User user =(User)userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }
}
