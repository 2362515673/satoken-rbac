package com.liu.rbac.model.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Data
public class SaveRoleDTO {
    @NotBlank(message = "角色名称不能为空")
    @Max(value = 8, message = "角色名称不能超过8个字符")
    private String name;
    private String remark;
}
