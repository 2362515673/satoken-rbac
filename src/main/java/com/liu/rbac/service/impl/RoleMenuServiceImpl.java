package com.liu.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.rbac.model.entity.RoleMenu;
import com.liu.rbac.service.RoleMenuService;
import com.liu.rbac.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @author liun
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
        implements RoleMenuService {
    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<RoleMenu> getRoleMenuByRoleIds(Collection<Long> roleIds) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(RoleMenu::getRoleId, roleIds);
        return roleMenuMapper.selectList(queryWrapper);
    }
}




