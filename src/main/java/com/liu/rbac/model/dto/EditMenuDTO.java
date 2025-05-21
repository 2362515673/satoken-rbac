package com.liu.rbac.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class EditMenuDTO extends SaveMenuDTO {
    @ApiModelProperty(dataType = "Long", required = true, value = "菜单id")
    @NotNull(message = "id不能为空")
    private Long id;
}
