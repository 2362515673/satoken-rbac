package com.liu.rbac.service;

import com.liu.rbac.model.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author liun
 */
public interface UserRoleService extends IService<UserRole> {
    /**
     * 根据用户id查询用户角色
     *
     * @param userIds 用户id集合
     * @return 所有用户的角色集合
     */
    List<UserRole> getUserRoleInUserIds(Collection<Long> userIds);

    /**
     * 根据用户id修改用户角色
     *
     * @param userId  用户id
     * @param roleIds 角色id集合
     */
    void editUserRoleByUserId(Long userId, List<Long> roleIds);
}
