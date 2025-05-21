package com.liu.rbac.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.rbac.constant.ErrorCode;
import com.liu.rbac.constant.MenuTypeEnum;
import com.liu.rbac.exception.BaseException;
import com.liu.rbac.model.dto.EditMenuDTO;
import com.liu.rbac.model.dto.SaveMenuDTO;
import com.liu.rbac.model.entity.Menu;
import com.liu.rbac.model.entity.RoleMenu;
import com.liu.rbac.model.entity.UserRole;
import com.liu.rbac.model.vo.MenuOptionsVO;
import com.liu.rbac.model.vo.MenuVO;
import com.liu.rbac.model.vo.RouterVO;
import com.liu.rbac.model.vo.TreeMenuVO;
import com.liu.rbac.service.MenuService;
import com.liu.rbac.mapper.MenuMapper;
import com.liu.rbac.service.RoleMenuService;
import com.liu.rbac.service.UserRoleService;
import com.liu.rbac.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liun
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
        implements MenuService {
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private UserService userService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private RoleMenuService roleMenuService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<TreeMenuVO> getTreeMenu() {
        List<MenuVO> list = lambdaQuery()
                .select(Menu::getId,
                        Menu::getParentId,
                        Menu::getName,
                        Menu::getCode,
                        Menu::getIcon,
                        Menu::getTitle,
                        Menu::getPath,
                        Menu::getUrl,
                        Menu::getType,
                        Menu::getOrder,
                        Menu::getCreateTime,
                        Menu::getUpdateTime)
                .list()
                .stream()
                .map(item -> BeanUtil.toBean(item, MenuVO.class))
                .collect(Collectors.toList());
        List<TreeMenuVO> topMenu = list.stream()
                .filter(item -> item.getParentId() == null)
                .map(item -> {
                    TreeMenuVO treeMenuVO = new TreeMenuVO();
                    treeMenuVO.setMenu(item);
                    return treeMenuVO;
                }).collect(Collectors.toList());
        List<MenuVO> childrenList = list.stream().filter(item -> item.getParentId() != null).collect(Collectors.toList());
        topMenu.forEach(item -> getTreeRecursion(item, childrenList));
        return topMenu;
    }

    @Override
    public Boolean saveMenu(SaveMenuDTO dto) {
        Menu menu = BeanUtil.toBean(dto, Menu.class);
        if (ObjectUtils.isNotEmpty(dto.getParentId()) && !lambdaQuery().eq(Menu::getId, menu.getParentId()).exists()) {
            throw new BaseException(ErrorCode.PARAMS_ERROR, "父级菜单不存在");
        }
        return save(menu);
    }

    @Override
    public List<MenuOptionsVO> getTreeMenuOptions() {
        List<TreeMenuVO> treeMenu = getTreeMenu();
        return getTreeOptionsRecursion(treeMenu);
    }

    @Override
    public Boolean editMenu(EditMenuDTO dto) {
        if (ObjectUtils.isNotEmpty(dto.getParentId()) && !lambdaQuery().eq(Menu::getId, dto.getParentId()).exists()) {
            throw new BaseException(ErrorCode.PARAMS_ERROR, "父级菜单不存在");
        }
        return updateById(BeanUtil.toBean(dto, Menu.class));
    }

    @Override
    public List<MenuVO> getUserMenuByUserId(Long userId) {
        List<UserRole> userRoleList = userRoleService.getUserRoleInUserIds(List.of(userId));
        if (CollectionUtils.isEmpty(userRoleList)) {
            return List.of();
        }
        return getMenuByRoleIds(userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList()));
    }

    @Override
    public List<MenuVO> getMenuByRoleId(Long roleId) {
        return getMenuByRoleIds(List.of(roleId));
    }

    @Override
    public List<MenuVO> getMenuByRoleIds(Collection<Long> roleIds) {
        List<RoleMenu> roleMenuList = roleMenuService.getRoleMenuByRoleIds(roleIds);
        if (CollectionUtils.isEmpty(roleMenuList)) {
            return List.of();
        }
        return menuMapper.selectBatchIds(roleMenuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toList()))
                .stream()
                .map(item -> BeanUtil.toBean(item, MenuVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RouterVO> getCurrentUserRouterTree() {
        // 1. 查询当前用户对应的角色
        long userId = StpUtil.getLoginIdAsLong();
        List<UserRole> userRoles = userRoleService.getUserRoleInUserIds(List.of(userId));
        // 2. 获取当前用户对应的角色ids
        List<Long> userRoleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        // 3. 根据角色ids获取对应的菜单列表
        List<MenuVO> menuList = getMenuByRoleIds(userRoleIds);
        // 4. 判断菜单集合是否为空
        if (CollectionUtils.isEmpty(menuList)) {
            return List.of();
        }
        // 5. 过滤按钮
        menuList = menuList.stream().filter(item -> !MenuTypeEnum.BUTTON.getCode().equals(item.getType())).collect(Collectors.toList());
        // 5. 获取菜单树
        return makeRouterTree(menuList, 0L);
    }

    /**
     * 递归获取菜单树
     *
     * @param fatherMenu 父级顶级菜单
     * @param list       菜单列表
     */
    private void getTreeRecursion(TreeMenuVO fatherMenu, List<MenuVO> list) {
        List<TreeMenuVO> treeList = new ArrayList<>();
        list.forEach(item -> {
            if (item.getParentId().equals(fatherMenu.getMenu().getId())) {
                TreeMenuVO treeMenuVO = new TreeMenuVO();
                treeMenuVO.setMenu(item);
                treeList.add(treeMenuVO);
            }
        });
        fatherMenu.setChildren(treeList);
        if (CollectionUtils.isEmpty(treeList)) {
            return;
        }

        treeList.forEach(item -> getTreeRecursion(item, list));
    }

    private List<MenuOptionsVO> getTreeOptionsRecursion(List<TreeMenuVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<MenuOptionsVO> optionsList = new ArrayList<>();

        list.forEach(item -> {
            MenuOptionsVO menuOptionsVO = new MenuOptionsVO();
            menuOptionsVO.setLabel(item.getMenu().getTitle());
            menuOptionsVO.setKey(item.getMenu().getId());
            menuOptionsVO.setChildren(getTreeOptionsRecursion(item.getChildren()));
            optionsList.add(menuOptionsVO);
        });
        return optionsList;
    }

    private List<RouterVO> makeRouterTree(List<MenuVO> menuList, Long parentId) {
        List<RouterVO> routerList = new ArrayList<>();
        menuList.stream()
                .filter(item -> item.getParentId().equals(parentId))
                .forEach(item -> {
                    RouterVO routerVO = new RouterVO();
                    routerVO.setName(item.getName());
                    routerVO.setPath(item.getPath());
                    List<RouterVO> children = makeRouterTree(menuList, item.getId());
                    routerVO.setChildren(children);
                    if (item.getParentId().equals(0L)) {
                        routerVO.setComponent("Layout");
                    } else {
                        routerVO.setComponent(item.getUrl());
                    }

                    routerVO.setMeta(new RouterVO.Meta(item.getTitle(), item.getIcon(), List.of(item.getCode())));

                    routerList.add(routerVO);
                });
        return routerList;
    }
}




