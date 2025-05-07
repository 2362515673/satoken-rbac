package com.liu.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.rbac.constant.ErrorCode;
import com.liu.rbac.model.entity.UserRole;
import com.liu.rbac.service.UserRoleService;
import com.liu.rbac.mapper.UserRoleMapper;
import com.liu.rbac.utils.ThrowUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liun
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
        implements UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public List<UserRole> getUserRoleInUserIds(Collection<Long> userIds) {
        return lambdaQuery().in(UserRole::getUserId, userIds).list();
    }

    @Transactional
    @Override
    public void editUserRoleByUserId(Long userId, List<Long> roleIds) {
        List<UserRole> list = lambdaQuery().eq(UserRole::getUserId, userId).list();
        List<Long> oldRoleIds = list.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<Long> collect = roleIds.stream().filter(item -> !oldRoleIds.contains(item)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            List<UserRole> userRoleList = collect.stream().map(roleId -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                return userRole;
            }).collect(Collectors.toList());
            ThrowUtils.throwIf(!saveBatch(userRoleList), ErrorCode.OPERATION_ERROR, "添加用户角色时出现错误");
        }
        collect = oldRoleIds.stream().filter(item -> !roleIds.contains(item)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId).in(UserRole::getRoleId, collect));
        }
    }
}




