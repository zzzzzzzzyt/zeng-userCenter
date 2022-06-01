package com.zeng.usercenter.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeng.usercenter.common.ErrorCode;
import com.zeng.usercenter.exception.BusinessException;
import com.zeng.usercenter.mapper.UserMapper;
import com.zeng.usercenter.model.domain.User;
import com.zeng.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zeng.usercenter.constants.UserConstant.USER_LOGIN_STATUS;

/**
 * @author 祝英台炸油条
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2022-05-16 16:30:05
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "zeng";
    /**
     * 正则表达式 非法字符匹配
     */
    private static final String ILLEGAL_PATTERN = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    /**
     * 用户登录态键
     */

    @Resource
    private UserMapper userMapper;

    /**
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 重复输入的密码
     * @param planetCode    星球编号
     * @return 返回用户id
     */
    @Override
    public long userRegistry(String userAccount, String userPassword, String checkPassword, String planetCode) {
        //引入方法进行校验 对应的字段们是否为空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "用户账号为空");
        }

        //用户的账户长度不能小于4位
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "用户账号过短");
        }

        //用户的密码长度不能小于8位
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "用户密码过短");
        }

        //验证星球编号 不能大于5 如果大于5则返回
        if (planetCode.length() > 5) throw new BusinessException(ErrorCode.PARAM_ERROR, "星球编号过长");

        //账户不包括特殊字符
        Matcher matcher = Pattern.compile(ILLEGAL_PATTERN).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "填写账户存在异常信息");
        }

        //密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "两次密码输入不一致");
        }

        //账户不能重复  就是需要进行查询了
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_account", userAccount);
        if (userMapper.selectCount(userQueryWrapper) > 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "数据库中已存在相应账号");
        }


        //星球编号不能重复
        userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("planet_code", planetCode);
        if (userMapper.selectCount(userQueryWrapper) > 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "数据库中已存在相应星球号");
        }

        //对密码进行加盐加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes(StandardCharsets.UTF_8));
        //插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        //必须插入成功
        if (!this.save(user)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "所请求的数据，插入失败");
        }
        return user.getId();
    }


    /**
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request      请求中信息封装
     * @return 返回登录对象
     */
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //首先要做的肯定是校验用户
        if (StringUtils.isAnyBlank(userAccount, userPassword))
            throw new BusinessException(ErrorCode.PARAM_ERROR, "请求参数为空");
        //用户的账户长度不能小于4位
        if (userAccount.length() < 4) throw new BusinessException(ErrorCode.PARAM_ERROR, "用户密码过短");
        //用户的密码长度不能小于8位
        if (userPassword.length() < 8) throw new BusinessException(ErrorCode.PARAM_ERROR, "用户密码过长");
        //账户不包括特殊字符
        Matcher matcher = Pattern.compile(ILLEGAL_PATTERN).matcher(userAccount);
        if (matcher.find()) throw new BusinessException(ErrorCode.PARAM_ERROR, "账号包含特殊字符");
        //进入数据库进行查询  查询是否存在相应的对象
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes(StandardCharsets.UTF_8));
        queryWrapper.eq("user_account", userAccount);
        queryWrapper.eq("user_password", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("Sorry,we count error.The account can not match password");
            throw new BusinessException(ErrorCode.PARAM_ERROR, "用户不存在");
        }
        //如果到这了 就带真正意义上得到了相应的对象 进行数据的脱敏
        User safeUser = getSafeUser(user);
        //存入对应的session  也就是进行用户封装态的确定
        HttpSession session = request.getSession();
        session.setAttribute(USER_LOGIN_STATUS, safeUser);
        return safeUser;
    }


    /**
     * 进行用户脱敏
     *
     * @param originUser
     * @return
     */
    public User getSafeUser(User originUser) {
        //注意要判空
        if (originUser == null) throw new BusinessException(ErrorCode.PARAM_ERROR, "对象为空");
        User safeUser = new User();
        safeUser.setId(originUser.getId());
        safeUser.setUserName(originUser.getUserName());
        safeUser.setUserAccount(originUser.getUserAccount());
        safeUser.setUserPassword("");
        safeUser.setAvatarUrl(originUser.getAvatarUrl());
        safeUser.setGender(originUser.getGender());
        safeUser.setPhone(originUser.getPhone());
        safeUser.setEmail(originUser.getEmail());
        safeUser.setPlanetCode(originUser.getPlanetCode());
        safeUser.setUserRole(originUser.getUserRole());
        safeUser.setUserStatus(originUser.getUserStatus());
        safeUser.setCreateTime(originUser.getCreateTime());
        safeUser.setUpdateTime(originUser.getUpdateTime());
        return safeUser;
    }

    /**
     * 删除用户登录状态
     *
     * @param request 用户请求
     */
    @Override
    public Integer userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATUS);
        return 1;
    }
}




