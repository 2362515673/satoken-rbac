package com.liu.rbac.model.vo;

import lombok.Data;

@Data
public class UserLoginVO {
    private UserVO user;
    private String token;
}
