package com.zeng.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeng.usercenter.model.domain.User;
import com.zeng.usercenter.service.UserService;
import com.zeng.usercenter.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author asus
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-05-16 16:30:05
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
    //合法的格式
    // static final
    private static final String SALT = "zeng";
    private static final String ILLEGAL_PATTERN = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    @Resource
    private UserMapper userMapper;

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
        //用户的密码长度不能小于8位
        if(userPassword.length()<8)return -1;
        //账户不包括特殊字符

        Matcher matcher = Pattern.compile(ILLEGAL_PATTERN).matcher(userAccount);
        if (matcher.find())return -1;
        //密码和校验密码相同
        if (!userPassword.equals(checkPassword))return -1;
        //账户不能重复  就是需要进行查询了
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_account",userAccount);
        if (userMapper.selectCount(userQueryWrapper)>0) return -1;
        //对密码进行加盐加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes(StandardCharsets.UTF_8));
        //插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        //必须插入成功
        if (!this.save(user)) {
            return -1;
        }
        return user.getId();
    }
}




