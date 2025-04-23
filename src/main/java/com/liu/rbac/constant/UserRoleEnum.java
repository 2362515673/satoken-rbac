package com.liu.rbac.constant;

import lombok.Getter;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum UserRoleEnum {
    USER("用户", "user"),
    ADMIN("管理员", "admin"),
    BIN("封号", "bin");

    private final String text;
    private final String value;

    UserRoleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public static List<String> getValueList() {
        return Arrays.stream(values()).map(UserRoleEnum::getValue).collect(Collectors.toList());
    }

    public static UserRoleEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        for (UserRoleEnum userRole : values()) {
            if (userRole.value.equals(value)) {
                return userRole;
            }
        }
        return null;
    }
}
