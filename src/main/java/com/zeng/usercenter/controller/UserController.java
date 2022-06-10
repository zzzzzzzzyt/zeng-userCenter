package com.zeng.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zeng.usercenter.common.BaseResponse;
import com.zeng.usercenter.common.ErrorCode;
import com.zeng.usercenter.common.ResultUtils;
import com.zeng.usercenter.exception.BusinessException;
import com.zeng.usercenter.model.domain.User;
import com.zeng.usercenter.model.request.UserLoginRequest;
import com.zeng.usercenter.model.request.UserRegisterRequest;
import com.zeng.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

import static com.zeng.usercenter.constants.UserConstant.ADMIN_ROLE;
import static com.zeng.usercenter.constants.UserConstant.USER_LOGIN_STATUS;

/**
 * 用户接口 前端请求获取相应的json或者返回值  在前端上进行显示
 *
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
    public BaseResponse<Long> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "请求request为空");
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        long userID = userService.userRegistry(userAccount, userPassword, checkPassword, planetCode);
        return ResultUtils.success(userID);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public BaseResponse<User> loginUser(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        //进行简单的测试
        if (StringUtils.isAnyBlank(userAccount, userPassword))
            throw new BusinessException(ErrorCode.NULL_ERROR, "请求参数为空");
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }


    /**
     * 搜索对应用户
     */
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUser(String username, HttpServletRequest request) {
        //搜索之前要进行鉴权 如果鉴权失败 则返回空
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "非管理员无权限");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(username)) {
            queryWrapper.like("user_name", username);
        }
        List<User> list = userService.list(queryWrapper);
        List<User> userCollect = list.stream().map(user -> userService.getSafeUser(user)).collect(Collectors.toList());
        return ResultUtils.success(userCollect);
    }


    /**
     * 获取当前用户信息
     *
     * @param request 请求
     * @return 返回脱敏过后的用户对应信息
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        if (userObj == null) throw new BusinessException(ErrorCode.NOT_LOGIN, "暂未登录");
        User currentUser = (User) userObj;
        //TODO 这边还是需要判断用户是否合法
        User user = userService.getById(currentUser.getId());
        User safeUser = userService.getSafeUser(user);
        return ResultUtils.success(safeUser);
    }


    /**
     * 删除对应用户
     */

    @GetMapping("/deleteUser")
    public BaseResponse<User> deleteUser(@RequestParam(value = "id") int id) {
        userService.removeById(id);
        return ResultUtils.success(null);
    }

    /**
     * 用户登录
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> logoutUser(HttpServletRequest request) {
        if (request == null) throw new BusinessException(ErrorCode.NULL_ERROR, "request为null");
        Integer userLogout = userService.userLogout(request);
        return ResultUtils.success(userLogout);
    }

    //判断用户是否为管理员
    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object userObj = session.getAttribute(USER_LOGIN_STATUS);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }
}
