package com.liu.rbac.model.dto;

import com.liu.rbac.utils.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryRoleDTO extends PageRequest {
    @ApiModelProperty(dataType = "String", value = "角色名称")
    private String name;

    @ApiModelProperty(dataType = "Integer", value = "角色状态")
    private Integer status;
}
