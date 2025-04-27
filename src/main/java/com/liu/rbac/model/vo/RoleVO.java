package com.liu.rbac.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleVO {
    private Long id;
    private String name;
    private String remark;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
