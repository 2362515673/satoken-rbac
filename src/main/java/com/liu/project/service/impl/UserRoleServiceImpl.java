package com.liu.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.project.model.entity.UserRole;
import com.liu.project.service.UserRoleService;
import com.liu.project.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author 86191
* @description 针对表【sys_user_role(用户角色关联表)】的数据库操作Service实现
* @createDate 2025-04-23 23:51:50
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




