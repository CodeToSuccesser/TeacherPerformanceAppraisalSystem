use teacher_performance;

-- 菜单表
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`value` VARCHAR(100) DEFAULT NULL COMMENT '菜单值',
	`label` VARCHAR(100) DEFAULT NULL COMMENT '菜单标示，web页面显示内容',
	`path` VARCHAR(200) DEFAULT NULL COMMENT '控制路径，web页面路由路径',
	`iconType` VARCHAR(100) DEFAULT NULL COMMENT '图标类型',
	`level` TINYINT(4) DEFAULT NULL COMMENT '菜单等级，0 顶级菜单，依次递增',
	`parent_value` VARCHAR(100) DEFAULT NULL COMMENT '父级菜单值',
	`full_value` VARCHAR(100) DEFAULT NULL COMMENT '菜单全值，从顶级菜单到本省，竖线\'|\'分割',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '账号逻辑删除标识, 0 无删除, 1 已删除',
	UNIQUE KEY `VALUE_UNIQUE_INDEX` (`value`) USING BTREE,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='系统路由菜单权限表';
-- 插入菜单路由
INSERT INTO `system_menu`(`id`, `value`, `label`, `path`, `iconType`, `level`, `parent_value`, `full_value`, `create_time`, `is_deleted`)VALUES
(1, 'info', '个人中心', '/info', 'user', 0, '', 'info', CURRENT_TIMESTAMP, 0),
(2, 'course', '教学管理', '/course', 'el-icon-s-help', 0, '', 'course', CURRENT_TIMESTAMP, 0),
(3, 'course-hour', '课时信息', '/course-hour', 'table', 1, 'course', 'course|course-hour', CURRENT_TIMESTAMP, 0),
(4, 'course-paper', '论文指导信息', '/paper', 'education', 1, 'course', 'course|course-paper', CURRENT_TIMESTAMP, 0),
(5, 'course-job', '实习带队信息', '/job', 'tree', 1, 'course', 'course|course-job', CURRENT_TIMESTAMP, 0),
(6, 'modify', '我的修改', '/modify', 'nested', 0, '', 'modify', CURRENT_TIMESTAMP, 0),
(7, 'modify-finished', '已完成', '/finished', 'nested', 1, 'modify', 'modify|modify-finished', CURRENT_TIMESTAMP, 0),
(8, 'modify-modifying', '进行中', '/modifying', 'list', 1, 'modify', 'modify|modify-modifying', CURRENT_TIMESTAMP, 0),
(9, 'assess', '考核结果', '/assess', 'form', 0, '', 'assess', CURRENT_TIMESTAMP, 0),
(10, 'courseManage', '课程管理', '/courseManage', 'form', 0, '', 'courseManage', CURRENT_TIMESTAMP, 0),
(11, 'userManage', '用户管理', '/userManage', 'form', 0, '', 'userManage', CURRENT_TIMESTAMP, 0),
(12, 'aduitManage', '操作记录', '/aduitManage', 'form', 0, '', 'aduitManage', CURRENT_TIMESTAMP, 0),
(13, 'systemManage', '系统管理', '/systemManage', 'form', 0, '', 'systemManage', CURRENT_TIMESTAMP, 0);

-- 角色表
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role` (
  `id` BIGINT(20) NOT NULL COMMENT '主键',
  `name` VARCHAR(100) NOT NULL UNIQUE COMMENT '角色名称',
  `menus_value` VARCHAR(1000) DEFAULT NULL COMMENT '角色对于菜单目录权限，用逗号拼接，如：memuA,menuB1',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '账号逻辑删除标识, 0 无删除, 1 已删除',
  `remark` VARCHAR(500) DEFAULT NULL,
  PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色表';
-- 新建角色，0教师 1管理员
INSERT INTO `system_role`(`id`, `name`, `menus_value`, `is_deleted`, `remark`) VALUES
(0, '教师角色', 'info,course,course-hour,course-paper,course-job,modify,modify-finished,modify-modifying,assess', 0, '');
INSERT INTO `system_role`(`id`, `name`, `menus_value`, `is_deleted`, `remark`) VALUES
(1, '管理员角色', 'info,course,course-hour,course-paper,course-job,modify,modify-finished,modify-modifying,assess,courseManage,userManage,aduitManage,systemManage', 0, '');

-- 用户表
DROP TABLE IF EXISTS `user_msg`;
CREATE TABLE `user_msg`(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`user_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '用户姓名',
	`log_name` VARCHAR(32) NOT NULL UNIQUE COMMENT '唯一登录名, 默认教师工号',
	`log_password` VARCHAR(128) NOT NULL DEFAULT '123456' COMMENT '登录密码, 初始密码123456',
	`contact` VARCHAR(60) DEFAULT NULL COMMENT '联系方式, 手机号/邮箱, 可为空',
	`portrait` VARCHAR(255) DEFAULT NULL COMMENT '头像图片url, 可为空',
	`admin_id` BIGINT(20) DEFAULT NULL COMMENT '创建账号的管理员编号, 可为空',
	`roles_name` VARCHAR(1000) DEFAULT NULL COMMENT '用户角色，用逗号拼接，如：role1,role2',
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '账号逻辑删除标识, 0 无删除, 1 已删除',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	UNIQUE INDEX `LOG_NAME_INDEX`(`log_name`) USING BTREE,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用户账号信息表';
-- 迁移数据
INSERT INTO `user_msg`(`user_name`, `log_name`, `log_password`, `contact`, `portrait`, `admin_id`, `roles_name`)(
	SELECT `admin_name` AS `user_name`
	, `log_name`
	, `log_password`
	, `contact`
	, `portrait`
	, null as `admin_id`
	, '1'
	FROM `admin_msg`
);
INSERT INTO `user_msg`(`user_name`, `log_name`, `log_password`, `contact`, `portrait`, `admin_id`, `roles_name`)(
	SELECT `teacher_name` AS `user_name`
	, `log_name`
	, `log_password`
	, `contact`
	, `portrait`
	, null as `admin_id`
	, '0'
	FROM `teacher_msg`
);
