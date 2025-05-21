package com.liu.rbac.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MenuTypeEnum {
    DIR(0, "目录"),
    MENU(1, "菜单"),
    BUTTON(2, "按钮");

    private final Integer code;
    private final String name;

    public static MenuTypeEnum getByName(Integer code) {
        for (MenuTypeEnum menuTypeEnum : MenuTypeEnum.values()) {
            if (menuTypeEnum.getCode().equals(code)) {
                return menuTypeEnum;
            }
        }
        return null;
    }

    public static MenuTypeEnum getByCode(String name) {
        for (MenuTypeEnum menuTypeEnum : MenuTypeEnum.values()) {
            if (menuTypeEnum.getName().equals(name)) {
                return menuTypeEnum;
            }
        }
        return null;
    }
}
