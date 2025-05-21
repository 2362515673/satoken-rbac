package com.liu.rbac.service;

import com.liu.rbac.model.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
* @author liun
*/
public interface RoleMenuService extends IService<RoleMenu> {
    /**
     * 根据角色id集合获取角色菜单关系
     * @param roleIds 角色id集合
     * @return 角色菜单关系列表
     */
    List<RoleMenu> getRoleMenuByRoleIds(Collection<Long> roleIds);
}
