package com.liu.rbac.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class EditUserDTO extends SaveUserDTO {
    @ApiModelProperty(dataType = "Long", required = true, value = "角色id")
    @NotNull(message = "角色id不能为空")
    private Long id;
}
