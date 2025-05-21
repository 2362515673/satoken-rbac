package com.liu.rbac.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.rbac.constant.ErrorCode;
import com.liu.rbac.constant.UserConstant;
import com.liu.rbac.exception.BaseException;
import com.liu.rbac.mapper.UserMapper;
import com.liu.rbac.model.dto.*;
import com.liu.rbac.model.entity.User;
import com.liu.rbac.model.entity.UserRole;
import com.liu.rbac.model.vo.UserLoginVO;
import com.liu.rbac.model.vo.UserRoleVO;
import com.liu.rbac.model.vo.UserVO;
import com.liu.rbac.service.UserRoleService;
import com.liu.rbac.service.UserService;
import com.liu.rbac.utils.ResultPage;
import com.liu.rbac.utils.ThrowUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleService userRoleService;

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
                User::getPassword,
                User::getName,
                User::getAvatar,
                User::getProfile
        );
        User user = userMapper.selectOne(queryWrapper);
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR, "账号或密码不存在");

        // 3. 校验密码是否正确
        String password = md5DigestAsHex(userPassword);
        ThrowUtils.throwIf(!password.equals(user.getPassword()), ErrorCode.NOT_FOUND_ERROR, "账号或密码不存在");

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

        String password = md5DigestAsHex(userPassword);
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

    @Transactional
    @Override
    public void saveUser(SaveUserDTO dto) {
        // 1. 判断账号名称是否重复
        boolean exists = lambdaQuery().eq(User::getAccount, dto.getAccount()).or().eq(User::getName, dto.getName()).exists();
        if (exists) {
            throw new BaseException(ErrorCode.PARAMS_ERROR, "账号或昵称已存在");
        }
        // 2. 封装数据
        User user = BeanUtil.toBean(dto, User.class);
        user.setPassword(md5DigestAsHex(user.getPassword()));
        // 3. 新增用户
        ThrowUtils.throwIf(!save(user), ErrorCode.OPERATION_ERROR, "添加用户时出现错误");

        if (CollUtil.isEmpty(dto.getRoleIds())) {
            return;
        }
        // 4. 新增用户添加角色
        List<UserRole> userRoleList = dto.getRoleIds().stream().map(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(roleId);
            return userRole;
        }).collect(Collectors.toList());
        ThrowUtils.throwIf(!userRoleService.saveBatch(userRoleList), ErrorCode.OPERATION_ERROR, "添加用户角色时出现错误");
    }

    @Override
    public ResultPage<UserRoleVO> getUserPage(QueryUserDTO dto) {
        // 1. 配置分页
        Page<User> page = Page.of(dto.getCurrent(), dto.getPageSize());
        // 2. 构建查询条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(dto.getName()), User::getName, dto.getName())
                .eq(ObjectUtils.isNotEmpty(dto.getStatus()), User::getStatus, dto.getStatus())
                .select(User::getId, User::getName, User::getSex, User::getAccount, User::getEmail, User::getPhone, User::getStatus, User::getProfile, User::getAvatar, User::getCreateTime, User::getUpdateTime);
        // 3. 执行查询
        page = userMapper.selectPage(page, queryWrapper);
        Set<Long> userIds = page.getRecords().stream().map(User::getId).collect(Collectors.toSet());
        List<UserRole> userRoleList = userRoleService.getUserRoleInUserIds(userIds);
        // 4. 构建返回对象
        return new ResultPage<>(page.getTotal(), page.getPages(), page.getRecords().stream().map(item -> {
            UserRoleVO userRoleVO = BeanUtil.toBean(item, UserRoleVO.class);
            userRoleVO.setRoleIds(new ArrayList<>());
            userRoleList.forEach(userRole -> {
                if (userRole.getUserId().equals(item.getId())) {
                    userRoleVO.getRoleIds().add(userRole.getRoleId());
                }
            });
            return userRoleVO;
        }).collect(Collectors.toList()));
    }


    @Transactional
    @Override
    public void editUser(EditUserDTO dto) {
        User user = BeanUtil.toBean(dto, User.class);
        ThrowUtils.throwIf(!updateById(user), ErrorCode.OPERATION_ERROR, "修改用户时出现错误");
        userRoleService.editUserRoleByUserId(dto.getId(), dto.getRoleIds());
    }

    /**
     * 字符串md5加密
     *
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    private String md5DigestAsHex(String str) {
        return DigestUtils.md5DigestAsHex((salt + str).getBytes());
    }
}
