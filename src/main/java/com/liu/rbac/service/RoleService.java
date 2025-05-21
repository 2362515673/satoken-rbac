package com.liu.rbac.service;

import com.liu.rbac.model.dto.AddRoleMenuDTO;
import com.liu.rbac.model.vo.RoleSelectVO;
import com.liu.rbac.utils.ResultPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.rbac.model.dto.EditRoleDTO;
import com.liu.rbac.model.dto.QueryRoleDTO;
import com.liu.rbac.model.dto.SaveRoleDTO;
import com.liu.rbac.model.entity.Role;
import com.liu.rbac.model.vo.RoleVO;

import java.util.List;

/**
 * @author liun
 */
public interface RoleService extends IService<Role> {

    /**
     * 添加角色
     *
     * @param dto 角色名称和角色备注
     * @return 角色id
     */
    Long saveRole(SaveRoleDTO dto);

    /**
     * 修改角色
     *
     * @param dto 角色id和角色基本信息
     * @return 是否修改成功
     */
    Boolean editRole(EditRoleDTO dto);

    /**
     * 分页查询角色信息
     *
     * @param dto 查询条件(角色名称和角色状态)
     * @return 分页角色信息
     */
    ResultPage<RoleVO> getRolePage(QueryRoleDTO dto);

    /**
     * 获取所有角色的id和名称
     *
     * @return 所有角色id和名称
     */
    List<RoleSelectVO> getRoleList();

    /**
     * 为角色添加菜单
     *
     * @param dto 角色id和菜单ids
     */
    void addRoleMenu(AddRoleMenuDTO dto);
}
