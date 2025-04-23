package com.liu.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.project.mapper.RoleMapper;
import com.liu.project.model.entity.Role;
import com.liu.project.service.RoleService;
import org.springframework.stereotype.Service;

/**
* @author 86191
* @description 针对表【sys_role(角色表)】的数据库操作Service实现
* @createDate 2025-04-23 23:28:01
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService {

}




