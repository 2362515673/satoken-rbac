package com.liu.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.rbac.model.dto.SaveRoleDTO;
import com.liu.rbac.model.entity.Role;

/**
* @author liun
*/
public interface RoleService extends IService<Role> {

    /**
     * 添加角色
     * @param dto 角色名称和角色备注
     * @return 角色id
     */
    Long saveRole(SaveRoleDTO dto);
}
