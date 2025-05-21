package com.liu.rbac.controller;

import com.liu.rbac.model.dto.EditMenuDTO;
import com.liu.rbac.model.dto.SaveMenuDTO;
import com.liu.rbac.model.vo.MenuOptionsVO;
import com.liu.rbac.model.vo.MenuVO;
import com.liu.rbac.model.vo.RouterVO;
import com.liu.rbac.model.vo.TreeMenuVO;
import com.liu.rbac.service.MenuService;
import com.liu.rbac.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/menu")
@Api(tags = "菜单模块")
public class MenuController {
    @Resource
    private MenuService menuService;

    @ApiOperation(value = "获取菜单树")
    @GetMapping("/tree")
    public Result<List<TreeMenuVO>> getTreeMenu() {
        return Result.success(menuService.getTreeMenu());
    }

    @ApiOperation(value = "获取菜单id和标题树结构")
    @GetMapping("/options")
    public Result<List<MenuOptionsVO>> getTreeMenuOptions() {
        return Result.success(menuService.getTreeMenuOptions());
    }

    @ApiOperation(value = "添加菜单")
    @PostMapping("/save")
    public Result<Boolean> saveMenu(@RequestBody @Valid SaveMenuDTO dto) {
        return Result.success(menuService.saveMenu(dto));
    }

    @ApiOperation(value = "编辑菜单")
    @PutMapping("/edit")
    public Result<Boolean> editMenu(@RequestBody @Valid EditMenuDTO dto) {
        return Result.success(menuService.editMenu(dto));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除菜单")
    public Result<Boolean> deleteById(@PathVariable @NotNull(message = "菜单id不能为空") Long id) {
        return Result.success(menuService.removeById(id));
    }

    @GetMapping("/user/list")
    @ApiOperation(value = "根据用户id获取当前用户的角色菜单")
    public Result<List<MenuVO>> getUserMenuByUserId(@NotNull(message = "用户id不能为空") Long userId) {
        return Result.success(menuService.getUserMenuByUserId(userId));
    }

    @GetMapping("/role/list")
    @ApiOperation(value = "根据角色id获取当前角色的菜单")
    public Result<List<MenuVO>> getMenuByRoleId(@NotNull(message = "角色id不能为空") Long roleId) {
        return Result.success(menuService.getMenuByRoleId(roleId));
    }

    @GetMapping("/get/current/menu/tree")
    @ApiOperation(value = "获取当前用户的路由树")
    public Result<List<RouterVO>> getCurrentUserRouterTree() {
        return Result.success(menuService.getCurrentUserRouterTree());
    }
}
