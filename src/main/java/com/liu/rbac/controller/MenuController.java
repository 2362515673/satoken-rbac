package com.liu.rbac.controller;

import com.liu.rbac.service.MenuService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/menu")
@Api(tags = "菜单模块")
public class MenuController {
    @Resource
    private MenuService menuService;
}
