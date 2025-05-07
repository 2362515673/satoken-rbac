package com.liu.rbac.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MenuVO {
    private Long id;

    private String title;

    private Long parentId;

    private String code;

    private String name;

    private String path;

    private String url;

    private Integer type;

    private String icon;

    private Integer order;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
