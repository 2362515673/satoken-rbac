package com.liu.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.rbac.constant.ErrorCode;
import com.liu.rbac.exception.BaseException;
import com.liu.rbac.mapper.RoleMapper;
import com.liu.rbac.model.dto.AddRoleMenuDTO;
import com.liu.rbac.model.dto.EditRoleDTO;
import com.liu.rbac.model.dto.QueryRoleDTO;
import com.liu.rbac.model.dto.SaveRoleDTO;
import com.liu.rbac.model.entity.Menu;
import com.liu.rbac.model.entity.Role;
import com.liu.rbac.model.entity.RoleMenu;
import com.liu.rbac.model.vo.RoleSelectVO;
import com.liu.rbac.model.vo.RoleVO;
import com.liu.rbac.service.MenuService;
import com.liu.rbac.service.RoleMenuService;
import com.liu.rbac.service.RoleService;
import com.liu.rbac.utils.ResultPage;
import com.liu.rbac.utils.ThrowUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liu
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private MenuService menuService;
    @Resource
    private RoleMenuService roleMenuService;

    @Override
    public Long saveRole(SaveRoleDTO dto) {
        Role role = BeanUtil.toBean(dto, Role.class);
        ThrowUtils.throwIf(!save(role), ErrorCode.SYSTEM_ERROR);
        return role.getId();
    }

    @Override
    public Boolean editRole(EditRoleDTO dto) {
        Role role = BeanUtil.toBean(dto, Role.class);
        if (roleMapper.updateById(role) == 0) {
            throw new BaseException(ErrorCode.PARAMS_ERROR, "角色不存在");
        }
        return true;
    }

    @Override
    public ResultPage<RoleVO> getRolePage(QueryRoleDTO dto) {
        // 1. 配置分页
        Page<Role> page = Page.of(dto.getCurrent(), dto.getPageSize());
        // 2. 构建查询条件
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(dto.getName()), Role::getName, dto.getName())
                .eq(ObjectUtils.isNotEmpty(dto.getStatus()), Role::getStatus, dto.getStatus())
                .select(Role::getId, Role::getName, Role::getStatus, Role::getRemark, Role::getUpdateTime, Role::getCreateTime);
        // 3. 执行查询
        page = roleMapper.selectPage(page, queryWrapper);
        // 4. 构建返回对象
        return new ResultPage<>(page.getTotal(), page.getPages(), page.getRecords().stream().map(item -> BeanUtil.toBean(item, RoleVO.class)).collect(Collectors.toList()));
    }

    @Override
    public List<RoleSelectVO> getRoleList() {
        return lambdaQuery().select(Role::getId, Role::getName)
                .list()
                .stream()
                .map(item -> BeanUtil.toBean(item, RoleSelectVO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void addRoleMenu(AddRoleMenuDTO dto) {
        List<Menu> menus = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dto.getMenuIds())){
            menus = menuService.listByIds(dto.getMenuIds());
        }

        List<RoleMenu> list = new ArrayList<>();
        menus.forEach(item -> {
            if (item == null) throw new BaseException(ErrorCode.PARAMS_ERROR, "菜单不存在");
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(dto.getRoleId());
            roleMenu.setMenuId(item.getId());
            list.add(roleMenu);
        });
        roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, dto.getRoleId()));
        roleMenuService.saveBatch(list);
    }
}




