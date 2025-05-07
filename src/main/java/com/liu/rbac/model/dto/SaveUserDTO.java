package com.liu.rbac.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class SaveUserDTO {

    @ApiModelProperty(dataType = "String", required = true, value = "账号")
    @NotBlank(message = "账号不能为空", groups = {SaveUserDTO.class})
    @Length(min = 8, max = 16, message = "账号不能小于8个字符或超过16个字符")
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty(dataType = "String", required = true, value = "密码")
    @NotBlank(message = "密码不能为空", groups = {SaveUserDTO.class})
    @Length(min = 6, max = 16, message = "密码不能小于6个字符或超过16个字符")
    private String password;


    /**
     * 用户昵称
     */
    @ApiModelProperty(dataType = "String", required = true, value = "用户昵称")
    @NotBlank(message = "用户昵称不能为空", groups = {SaveUserDTO.class})
    @Length(max = 8, message = "用户昵称不能超过8个字符")
    private String name;

    /**
     * 用户性别
     */
    @ApiModelProperty(dataType = "Integer", value = "用户性别")
    private Integer sex;

    /**
     * 用户头像
     */
    @ApiModelProperty(dataType = "Integer", value = "用户性别")
    @Length(max = 80, message = "头像地址过长")
    private String avatar;

    /**
     * 用户简介
     */
    @ApiModelProperty(dataType = "String", value = "用户简介")
    private String profile;

    /**
     * 邮箱
     */
    @ApiModelProperty(dataType = "String", value = "用户邮箱")
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty(dataType = "String", value = "用户手机号")
    private String phone;

    /**
     * 用户状态 0：禁用 1：启用
     */
    @ApiModelProperty(dataType = "String", value = "用户状态")
    private Integer status;

    /**
     * 角色id集合
     */
    @ApiModelProperty(dataType = "List", value = "角色id集合")
    private List<Long> roleIds;
}
