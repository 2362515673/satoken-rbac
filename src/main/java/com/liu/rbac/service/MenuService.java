package com.liu.rbac.service;

import com.liu.rbac.model.dto.EditMenuDTO;
import com.liu.rbac.model.dto.SaveMenuDTO;
import com.liu.rbac.model.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.rbac.model.vo.MenuOptionsVO;
import com.liu.rbac.model.vo.MenuVO;
import com.liu.rbac.model.vo.RouterVO;
import com.liu.rbac.model.vo.TreeMenuVO;

import java.util.Collection;
import java.util.List;

/**
 * @author liun
 */
public interface MenuService extends IService<Menu> {

    /**
     * 获取菜单树
     *
     * @return 菜单树
     */
    List<TreeMenuVO> getTreeMenu();

    /**
     * 添加菜单
     *
     * @param dto 菜单信息
     * @return 是否添加成功
     */
    Boolean saveMenu(SaveMenuDTO dto);

    /**
     * 获取菜单树选项
     *
     * @return 菜单树选项
     */
    List<MenuOptionsVO> getTreeMenuOptions();

    /**
     * 编辑菜单
     *
     * @param dto 菜单信息
     * @return 是否编辑成功
     */
    Boolean editMenu(EditMenuDTO dto);

    /**
     * 根据用户id获取菜单
     *
     * @param userId 用户id
     * @return 菜单列表
     */
    List<MenuVO> getUserMenuByUserId(Long userId);

    /**
     * 根据角色id获取菜单
     *
     * @param roleId 角色id
     * @return 菜单列表
     */
    List<MenuVO> getMenuByRoleId(Long roleId);

    /**
     * 根据角色id获取菜单
     *
     * @param roleIds 角色id集合
     * @return 菜单列表
     */
    List<MenuVO> getMenuByRoleIds(Collection<Long> roleIds);

    /**
     * 获取当前用户的路由树
     * @return 路由树
     */
    List<RouterVO> getCurrentUserRouterTree();
}
