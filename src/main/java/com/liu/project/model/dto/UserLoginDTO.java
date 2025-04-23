package com.liu.project.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserLoginDTO implements Serializable {
    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    @Length(min = 6, max = 12, message = "账号长度在6-12位之间")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 20, message = "密码长度在6-20位之间")
    private String password;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
