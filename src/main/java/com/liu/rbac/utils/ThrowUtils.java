package com.liu.rbac.utils;


import com.liu.rbac.constant.ErrorCode;
import com.liu.rbac.exception.BaseException;

public class ThrowUtils {
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛异常
     */
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BaseException(errorCode));
    }


    /**
     * 条件成立则抛异常
     */
    public static void throwIf(boolean condition, String message) {
        throwIf(condition, new BaseException(message));
    }

    /**
     * 条件成立则抛异常
     */
    public static void throwIf(boolean condition, ErrorCode errorCode, String message) {
        throwIf(condition, new BaseException(message, errorCode.getCode()));
    }
}
