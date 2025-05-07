package com.liu.rbac.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserRoleVO extends UserVO {
    private List<Long> roleIds;
}
