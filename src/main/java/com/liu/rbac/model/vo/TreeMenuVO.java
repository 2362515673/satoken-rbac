package com.liu.rbac.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class TreeMenuVO {
    private MenuVO menu;
    private List<TreeMenuVO> children;
}
