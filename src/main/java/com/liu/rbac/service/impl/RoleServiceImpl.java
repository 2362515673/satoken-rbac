package com.liu.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.rbac.constant.ErrorCode;
import com.liu.rbac.mapper.RoleMapper;
import com.liu.rbac.model.dto.SaveRoleDTO;
import com.liu.rbac.model.entity.Role;
import com.liu.rbac.service.RoleService;
import com.liu.rbac.utils.ThrowUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liu
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {
    @Resource
    private RoleMapper roleMapper;

    @Override
    public Long saveRole(SaveRoleDTO dto) {
        Role role = BeanUtil.toBean(dto, Role.class);
        ThrowUtils.throwIf(!save(role), ErrorCode.SYSTEM_ERROR);
        return role.getId();
    }
}




