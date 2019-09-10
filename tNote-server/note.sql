/**
  测试表sql
 */
CREATE TABLE `test_ta` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `text_blog` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/**
  note_users sql
 */
CREATE TABLE `note_users` (
                              `id` bigint(20) NOT NULL,
                              `user_name` varchar(45) NOT NULL COMMENT '登录名',
                              `password` varchar(45) NOT NULL COMMENT '加密之后的密码',
                              `real_name` varchar(45) NOT NULL COMMENT '用户真实姓名',
                              `slat` varchar(45) NOT NULL COMMENT '密码加密的盐',
                              `org_id` varchar(45) NOT NULL COMMENT '机构id',
                              `gmt_create` datetime NOT NULL COMMENT '创建日期',
                              `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日期',
                              `deleted` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除标记（1，删除，0 正常）默认0',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/**
  org机构表 sql
 */
CREATE TABLE `mynote`.`note_organization` (
                                              `id` BIGINT(20) NOT NULL,
                                              `title` VARCHAR(45) NOT NULL COMMENT '机构名称',
                                              `pid` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '父机构ID',
                                              `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                                              `gmt_modified` DATETIME NOT NULL COMMENT '修改时间',
                                              `deleted` TINYINT(2) NOT NULL DEFAULT 0 COMMENT '删除标记 0 正常1 删除',
                                              PRIMARY KEY (`id`));
/**
  note_log
 */
CREATE TABLE `note_log` (
                            `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '操作日志id',
                            `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作人',
                            `module` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '执行模块',
                            `methods` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '执行方法',
                            `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作内容',
                            `action_url` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求路径',
                            `ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'IP地址',
                            `gmt_create` datetime NOT NULL COMMENT '操作时间',
                            `commite` tinyint(2) NOT NULL COMMENT '执行描述（1:执行成功、2:执行失败）',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;