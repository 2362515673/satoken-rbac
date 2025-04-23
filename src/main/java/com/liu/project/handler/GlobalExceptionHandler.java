package com.liu.project.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import com.liu.project.constant.ErrorCode;
import com.liu.project.exception.BaseException;
import com.liu.project.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BaseException.class)
    public Result<Object> baseException(BaseException e) {
        log.error(e.getMessage());
        return Result.error(e.getMessage(), e.getCode());
    }

    @ExceptionHandler(NotRoleException.class)
    public Result<Object> notRoleExceptionHandler(NotRoleException e) {
        log.error("NotRoleException", e);
        return Result.error(ErrorCode.NO_AUTH_ERROR, "无权限");
    }

    @ExceptionHandler(NotLoginException.class)
    public Result<Object> notLoginExceptionHandler(NotLoginException e) {
        log.error("NotLoginException", e);
        if (e.getMessage().contains(NotLoginException.BE_REPLACED_MESSAGE)) {
            return Result.error(ErrorCode.NOT_LOGIN_ERROR, "该账号在异地登录");
        }
        return Result.error(ErrorCode.NOT_LOGIN_ERROR, "未登录");
    }

}
