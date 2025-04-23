package com.liu.rbac.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.rbac.constant.ErrorCode;
import com.liu.rbac.constant.UserConstant;
import com.liu.rbac.mapper.UserMapper;
import com.liu.rbac.model.dto.UserLoginDTO;
import com.liu.rbac.model.dto.UserRegisterDTO;
import com.liu.rbac.model.entity.User;
import com.liu.rbac.model.vo.UserLoginVO;
import com.liu.rbac.model.vo.UserVO;
import com.liu.rbac.service.UserService;
import com.liu.rbac.utils.ThrowUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 盐值，加密密码用的
     */
    @Value("${liu.salt}")
    private String salt;

    @Override
    public UserLoginVO doLogin(UserLoginDTO dto) {
        // 1. 校验参数
        String userAccount = dto.getAccount();
        String userPassword = dto.getPassword();

        // 2. 判断用户是否存在
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery(User.class).eq(User::getAccount, userAccount);
        queryWrapper.select(
                User::getId,
                User::getAccount,
                User::getName,
                User::getAvatar,
                User::getProfile
        );
        User user = userMapper.selectOne(queryWrapper);
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR,"账号或密码不存在");

        // 3. 校验密码是否正确
        String password = DigestUtils.md5DigestAsHex((salt + userPassword).getBytes());
        ThrowUtils.throwIf(!password.equals(user.getPassword()), ErrorCode.NOT_FOUND_ERROR,"账号或密码不存在");

        // Sa-Token 登录，并指定设备，同端登录互斥
        StpUtil.login(user.getId());
        // 将用户信息存入session
        StpUtil.getSession().set(UserConstant.USER_LOGIN_STATE, user);

        // 4. 返回用户 VO 对象
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setUser(BeanUtil.toBean(user, UserVO.class));
        userLoginVO.setToken(StpUtil.getTokenValue());
        return userLoginVO;
    }

    @Override
    public Boolean doRegister(UserRegisterDTO dto) {
        String userAccount = dto.getAccount();
        String userPassword = dto.getPassword();
        String userName = dto.getName();

        String password = DigestUtils.md5DigestAsHex((salt + userPassword).getBytes());
        boolean exists = lambdaQuery().eq(User::getAccount, userAccount).eq(User::getPassword, password).exists();
        ThrowUtils.throwIf(exists, ErrorCode.PARAMS_ERROR, "账号已存在");

        User user = new User();
        user.setName(userName);
        user.setAccount(userAccount);
        user.setPassword(password);

        return save(user);
    }

    @Override
    public void doLogout() {
        // 检验当前会话是否已经登录, 如果未登录，则抛出异常：`NotLoginException`
        // StpUtil.checkLogin();

        if (isLogin()) {
            StpUtil.logout();
        }
    }

    @Override
    public Boolean isLogin() {
        return StpUtil.isLogin();
    }

    @Override
    public UserVO getCurrent() {
        // 先判断是否以登录
        Object loginUserId = StpUtil.getLoginIdDefaultNull();
        ThrowUtils.throwIf(loginUserId == null, ErrorCode.NOT_LOGIN_ERROR);
        User user = userMapper.selectById((String) loginUserId);
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_LOGIN_ERROR);
        UserVO userVo = new UserVO();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }
}
