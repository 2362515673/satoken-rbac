package com.liu.rbac.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import com.liu.rbac.constant.ErrorCode;
import com.liu.rbac.exception.BaseException;
import com.liu.rbac.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLSyntaxErrorException;
import java.util.Objects;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 自定义异常
     */
    @ExceptionHandler(value = BaseException.class)
    public Result<Object> baseException(BaseException e) {
        log.error(e.getMessage());
        return Result.error(e.getMessage(), e.getCode());
    }

    @ExceptionHandler(value = SQLSyntaxErrorException.class)
    public Result<Object> sqlSyntaxErrorException(SQLSyntaxErrorException e) {
        log.error(e.getMessage());
        return Result.error("SQL错误", ErrorCode.SYSTEM_ERROR.getCode());
    }

    /**
     * 请求参数异常(spring-boot-starter-validation)
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<Object> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
        return Result.error(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(), ErrorCode.PARAMS_ERROR.getCode());
    }

    /**
     * satoken 权限异常处理
     */
    @ExceptionHandler(NotRoleException.class)
    public Result<Object> notRoleExceptionHandler(NotRoleException e) {
        log.error("NotRoleException", e);
        return Result.error(ErrorCode.NO_AUTH_ERROR, "无权限");
    }

    /**
     * satoken 登录异常处理
     */
    @ExceptionHandler(NotLoginException.class)
    public Result<Object> notLoginExceptionHandler(NotLoginException e) {
        log.error("NotLoginException", e);
        if (e.getMessage().contains(NotLoginException.BE_REPLACED_MESSAGE)) {
            return Result.error(ErrorCode.NOT_LOGIN_ERROR, "该账号在异地登录");
        }
        return Result.error(ErrorCode.NOT_LOGIN_ERROR, "未登录");
    }

}
