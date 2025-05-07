package com.liu.rbac.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SaveMenuDTO {
    @NotBlank(message = "菜单名称不能为空", groups = SaveMenuDTO.class)
    private String title;

    private Long parentId;

    @NotBlank(message = "菜单编码不能为空", groups = SaveMenuDTO.class)
    private String code;

    private String name;

    private String path;

    private String url;

    @NotNull(message = "菜单类型不能为空", groups = SaveMenuDTO.class)
    private Integer type;

    private String icon;

    private Integer order;
}
