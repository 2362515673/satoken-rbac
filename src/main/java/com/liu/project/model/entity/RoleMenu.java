package com.liu.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 角色菜单关联表
 */
@TableName(value ="sys_role_menu")
@Data
public class RoleMenu implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 菜单id
     */
    @TableField(value = "menu_id")
    private Long menuId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}