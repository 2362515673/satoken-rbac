package com.liu.rbac.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 路由
 * 注解的作用 @JsonInclude(JsonInclude.Include.NON_EMPTY)
 * null、集合数组等没有内容、空字符串等，都不会被序列化
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouterVO {
    private String path;
    private String name;
    private String component;
    private String redirect;
    private Meta meta;
    private List<RouterVO> children = new ArrayList<>();

    @Data
    @AllArgsConstructor
    public static class Meta {
        private String title;
        private String icon;
        private List<String> roles;
    }
}
