package com.liu.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.rbac.model.dto.UserLoginDTO;
import com.liu.rbac.model.dto.UserRegisterDTO;
import com.liu.rbac.model.entity.User;
import com.liu.rbac.model.vo.UserLoginVO;
import com.liu.rbac.model.vo.UserVO;

public interface UserService extends IService<User> {
    /**
     * 用户登录
     *
     * @param dto 账号 密码
     * @return 用户基本信息
     */
    UserLoginVO doLogin(UserLoginDTO dto);

    /**
     * 用户注册
     *
     * @param dto 用户注册信息
     * @return 是否注册成功
     */
    Boolean doRegister(UserRegisterDTO dto);

    /**
     * 用户注销
     */
    void doLogout();

    /**
     * 获取当前是否登录
     *
     * @return true=已登录，false=未登录
     */
    Boolean isLogin();

    /**
     * 获取用户登录后自己的信息
     *
     * @return 用户基本信息
     */
    UserVO getCurrent();
}
