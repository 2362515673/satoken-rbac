package com.liu.rbac.constant;

import lombok.Getter;

@Getter
public enum UserAndRoleEnum {
    START("启用", 1),
    DISABLE("禁用", 0);

    private final Integer code;
    private final String value;

    UserAndRoleEnum(String value, Integer code) {
        this.code = code;
        this.value = value;
    }
}
