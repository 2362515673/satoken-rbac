package com.liu.rbac.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class AddRoleMenuDTO {
    @NotNull(message = "角色id不能为空")
    private Long roleId;
    @NotNull(message = "菜单id不能为空")
    private List<Long> menuIds;
}
