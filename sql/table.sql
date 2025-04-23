CREATE TABLE `user`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `account`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '账号',
    `password`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
    `name`        varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT '用户昵称',
    `avatar`      varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '用户头像',
    `profile`     varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '用户简介',
    `email`       varchar(36) COLLATE utf8mb4_unicode_ci                                 DEFAULT NULL COMMENT '邮箱',
    `phone`       varchar(13) COLLATE utf8mb4_unicode_ci                                 DEFAULT NULL COMMENT '手机号',
    `sex`         tinyint                                                                DEFAULT NULL COMMENT '性别',
    `status`      tinyint                                                       NOT NULL DEFAULT '0' COMMENT '用户状态 0：禁用 1：启用',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint                                                       NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1862474293683130372
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户';

CREATE TABLE `sys_menu`
(
    `id`          bigint                                 NOT NULL AUTO_INCREMENT COMMENT 'id',
    `title`       varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单标题',
    `parent_id`   bigint                                          DEFAULT NULL COMMENT '父级菜单id',
    `code`        varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '权限字段',
    `name`        varchar(36) COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '路由名称',
    `path`        varchar(128) COLLATE utf8mb4_unicode_ci         DEFAULT NULL COMMENT '路由地址',
    `url`         varchar(128) COLLATE utf8mb4_unicode_ci         DEFAULT NULL COMMENT '组件路径',
    `type`        tinyint                                NOT NULL DEFAULT '0' COMMENT '菜单类型(0：目录，1：菜单，2：按钮)',
    `icon`        varchar(128) COLLATE utf8mb4_unicode_ci         DEFAULT NULL COMMENT '菜单图标',
    `order`       int                                             DEFAULT '0' COMMENT '排序字段',
    `create_time` datetime                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                               NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`   tinyint                                NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `title` (`title`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1862474293683130371
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='菜单表';


CREATE TABLE `sys_role`
(
    `id`          bigint                                 NOT NULL AUTO_INCREMENT COMMENT 'id',
    `name`        varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
    `type`        tinyint                                NOT NULL DEFAULT '0' COMMENT '角色类型',
    `remark`      varchar(128) COLLATE utf8mb4_unicode_ci         DEFAULT NULL COMMENT '备注',
    `create_time` datetime                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                               NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`   tinyint                                NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 28474293683130371
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='角色表';


CREATE TABLE `sys_role_menu`
(
    `id`      bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    `role_id` bigint NOT NULL COMMENT '角色id',
    `menu_id` bigint NOT NULL COMMENT '菜单id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1861476294683130371
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='角色菜单关联表';

CREATE TABLE `sys_user_role`
(
    `id`      bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id` bigint NOT NULL COMMENT '用户id',
    `role_id` bigint NOT NULL COMMENT '角色id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1234568784948448844
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户角色关联表';