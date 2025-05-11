package com.liu.rbac.controller;

import com.liu.rbac.model.dto.SaveMenuDTO;
import com.liu.rbac.model.vo.MenuOptionsVO;
import com.liu.rbac.model.vo.TreeMenuVO;
import com.liu.rbac.service.MenuService;
import com.liu.rbac.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
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
}
