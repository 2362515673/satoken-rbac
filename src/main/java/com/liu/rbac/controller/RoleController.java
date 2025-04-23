package com.liu.rbac.controller;

import com.liu.rbac.model.dto.SaveRoleDTO;
import com.liu.rbac.service.RoleService;
import com.liu.rbac.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/role")
@Api(tags = "角色模块")
public class RoleController {
    @Resource
    private RoleService roleService;

    @PostMapping("/add")
    public Result<Long> saveRole(@RequestBody SaveRoleDTO dto){
        return Result.success(roleService.saveRole(dto));
    }
}
