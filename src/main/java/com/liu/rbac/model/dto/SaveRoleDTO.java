package com.liu.rbac.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class SaveRoleDTO {
    @ApiModelProperty(dataType = "String", required = true, value = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    @Length(max = 8, message = "角色名称不能超过8个字符")
    private String name;

    @ApiModelProperty(dataType = "String", value = "角色备注")
    private String remark;

    @ApiModelProperty(dataType = "Integer", value = "角色状态")
    private Integer status;
}
