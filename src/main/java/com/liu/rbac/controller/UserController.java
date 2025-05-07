package com.liu.rbac.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.liu.rbac.model.dto.*;
import com.liu.rbac.model.vo.UserLoginVO;
import com.liu.rbac.model.vo.UserRoleVO;
import com.liu.rbac.model.vo.UserVO;
import com.liu.rbac.utils.Result;
import com.liu.rbac.service.UserService;
import com.liu.rbac.utils.ResultPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

    @ApiOperation(value = "添加用户")
    @PostMapping("/save")
    public Result<Boolean> saveUser(@RequestBody @Valid SaveUserDTO dto) {
        userService.saveUser(dto);
        return Result.success(true);
    }

    @ApiOperation(value = "分页获取用户所以信息(admin)")
    @GetMapping("/page")
    public Result<ResultPage<UserRoleVO>> getUserPage(QueryUserDTO dto) {
        return Result.success(userService.getUserPage(dto));
    }

    @ApiOperation(value = "修改用户")
    @PutMapping("/edit")
    public Result<Boolean> editUser(@RequestBody @Valid EditUserDTO dto) {
        userService.editUser(dto);
        return Result.success(true);
    }

    @ApiOperation(value = "根据id删除用户")
    @DeleteMapping("/delete/{id}")
    public Result<Boolean> deleteUserById(@PathVariable @NotNull(message = "id不能为空") Long id) {
        return Result.success(userService.removeById(id));
    }
}
