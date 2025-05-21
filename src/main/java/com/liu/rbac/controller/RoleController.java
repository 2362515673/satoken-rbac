package com.liu.rbac.controller;

import com.liu.rbac.constant.ErrorCode;
import com.liu.rbac.exception.BaseException;
import com.liu.rbac.model.dto.AddRoleMenuDTO;
import com.liu.rbac.model.vo.RoleSelectVO;
import com.liu.rbac.utils.ResultPage;
import com.liu.rbac.model.dto.EditRoleDTO;
import com.liu.rbac.model.dto.QueryRoleDTO;
import com.liu.rbac.model.dto.SaveRoleDTO;
import com.liu.rbac.model.vo.RoleVO;
import com.liu.rbac.service.RoleService;
import com.liu.rbac.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/role")
@Api(tags = "角色模块")
public class RoleController {
    @Resource
    private RoleService roleService;

    @PostMapping("/add")
    @ApiOperation(value = "添加系统角色")
    public Result<Long> saveRole(@RequestBody @Valid SaveRoleDTO dto) {
        return Result.success(roleService.saveRole(dto));
    }

    @PutMapping("/edit")
    @ApiOperation(value = "修改系统角色")
    public Result<Boolean> editRole(@RequestBody @Valid EditRoleDTO dto) {
        return Result.success(roleService.editRole(dto));
    }

    @GetMapping("/page")
    @ApiOperation(value = "分页查询系统角色")
    public Result<ResultPage<RoleVO>> getRolePage(QueryRoleDTO dto) {
        return Result.success(roleService.getRolePage(dto));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除系统角色")
    @ApiModelProperty(dataType = "Long", required = true, value = "角色id")
    public void deleteRole(@PathVariable @NotNull(message = "角色id不能为空") Long id) {
        if (!roleService.removeById(id)) {
            throw new BaseException(ErrorCode.PARAMS_ERROR, "角色不存在");
        }
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取所有角色的id和名称")
    public Result<List<RoleSelectVO>> getRoleList() {
        return Result.success(roleService.getRoleList());
    }

    @PostMapping("/add/menu")
    @ApiOperation(value = "为角色添加菜单")
    public Result<Boolean> addRoleMenu(@RequestBody @Valid AddRoleMenuDTO dto) {
        roleService.addRoleMenu(dto);
        return Result.success(true);
    }
}
