package com.liu.project.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.liu.project.model.dto.UserLoginDTO;
import com.liu.project.model.dto.UserRegisterDTO;
import com.liu.project.model.vo.UserLoginVO;
import com.liu.project.model.vo.UserVO;
import com.liu.project.utils.Result;
import com.liu.project.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Api(tags = "用户模块")
public class UserController {
    @Resource
    private UserService userService;

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Result<UserLoginVO> doLogin(@RequestBody @Valid UserLoginDTO dto) {
        return Result.success(userService.doLogin(dto));
    }

    @ApiOperation(value = "用户注销")
    @PostMapping("/logout")
    @SaCheckLogin
    public void doLogout() {
        userService.doLogout();
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public Result<Boolean> doRegister(@RequestBody @Valid UserRegisterDTO userRegisterDto) {
        return Result.success(userService.doRegister(userRegisterDto));
    }

    @ApiOperation(value = "获取当前是否登录")
    @GetMapping("/isLogin")
    public Result<Boolean> isLogin() {
        return Result.success(userService.isLogin());
    }

    @ApiOperation(value = "获取登录后的用户信息")
    @GetMapping("/current")
    @SaCheckLogin
    public Result<UserVO> getCurrent() {
        return Result.success(userService.getCurrent());
    }
}
