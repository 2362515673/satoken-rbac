package com.liu.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.rbac.model.entity.RoleMenu;
import com.liu.rbac.service.RoleMenuService;
import com.liu.rbac.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author liun
* @description 针对表【sys_role_menu(角色菜单关联表)】的数据库操作Service实现
* @createDate 2025-04-23 23:51:50
*/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
    implements RoleMenuService{

}




