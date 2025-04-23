package com.liu.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 系统菜单表
 */
@TableName(value ="sys_menu")
@Data
public class Menu implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 菜单标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 父级菜单id
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 权限字段
     */
    @TableField(value = "code")
    private String code;

    /**
     * 路由名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 路由地址
     */
    @TableField(value = "path")
    private String path;

    /**
     * 组件路径
     */
    @TableField(value = "url")
    private String url;

    /**
     * 菜单类型(0：目录，1：菜单，2：按钮)
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 菜单图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 排序字段
     */
    @TableField(value = "order")
    private Integer order;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}