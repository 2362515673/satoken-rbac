package com.liu.rbac.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class MenuOptionsVO {
    private String label;
    private Long key;
    private List<MenuOptionsVO> children;
}
