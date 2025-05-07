package com.liu.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.rbac.model.dto.*;
import com.liu.rbac.model.entity.User;
import com.liu.rbac.model.vo.UserLoginVO;
import com.liu.rbac.model.vo.UserRoleVO;
import com.liu.rbac.model.vo.UserVO;
import com.liu.rbac.utils.ResultPage;

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

    /**
     * 创建用户
     * @param dto 用户信息
     */
    void saveUser(SaveUserDTO dto);

    /**
     * 分页获取用户
     * @param dto 查询条件
     * @return 用户列表
     */
    ResultPage<UserRoleVO> getUserPage(QueryUserDTO dto);

    /**
     * 修改用户信息
     * @param dto 用户信息
     */
    void editUser(EditUserDTO dto);
}
