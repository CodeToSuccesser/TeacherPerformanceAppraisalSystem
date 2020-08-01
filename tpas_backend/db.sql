# ----------------------------------------SQL v1.0.0----------------------------------------
# --2020.07.25
DROP DATABASE IF EXISTS `teacher_performance_dev`;
CREATE  DATABASE `teacher_performance_dev`;

USE `teacher_performance_pro`;

CREATE TABLE `teacher_msg`(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`teacher_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '教师姓名',
	`log_name` VARCHAR(32) NOT NULL UNIQUE COMMENT '唯一登录名, 默认教师工号',
	`log_password` VARCHAR(128) NOT NULL DEFAULT '123456' COMMENT '登录密码, 初始密码123456',
	`contact` VARCHAR(60) DEFAULT NULL COMMENT '联系方式, 手机号/邮箱, 可为空',
	`portrait` VARCHAR(255) DEFAULT NULL COMMENT '头像图片url, 可为空',
	`admin_id` BIGINT(20) DEFAULT NULL COMMENT '创建账号的管理员编号, 可为空',
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '账号逻辑删除标识, 0 无删除, 1 已删除',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='教师账号信息表';

CREATE TABLE `admin_msg`(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`admin_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '管理员姓名',
	`log_name` VARCHAR(32) NOT NULL UNIQUE COMMENT '唯一登录名, 默认教务员工号',
	`log_password` VARCHAR(128) NOT NULL DEFAULT '123456' COMMENT '登录密码, 初始密码123456',
	`contact` VARCHAR(60) DEFAULT NULL COMMENT '联系方式, 手机号/邮箱, 可为空',
	`portrait` VARCHAR(255) DEFAULT NULL COMMENT '头像图片url, 可为空',
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '账号逻辑删除标识, 0 无删除, 1 已删除',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='管理员账号信息表';

CREATE TABLE `params`(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`c_num` TINYINT(4) NOT NULL COMMENT 'Cn的n值, 1 表示C1, range[1, 13]',
	`c_opction` TINYINT(4) NOT NULL COMMENT 'm表示第m个Cn',
	`value` DECIMAL(5, 3) NOT NULL COMMENT '参数权值',
	`admin_id` BIGINT(20) NOT NULL COMMENT '操作管理员编号',
	`remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='绩效权值参数表';

CREATE TABLE `major`(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`admin_id` BIGINT(20) NOT NULL COMMENT '操作管理员编号',
	`major_code` VARCHAR(128) NOT NULL UNIQUE COMMENT '专业代码号',
	`major_name` VARCHAR(128) NOT NULL COMMENT '专业名称',
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '账号逻辑删除标识, 0 无删除, 1 已删除',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='专业信息表';

CREATE TABLE `course_base`(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`course_name` VARCHAR(128) NOT NULL COMMENT '课程名称',
	`course_character` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '课程性质, 默认0 必修, 1 选修',
	`course_type` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '课程类别, 默认0 专业方向, 1 专业核心, 2 教师教育, 3 实践教学, 4 大类教育',
	`course_cridet` DECIMAL(3, 2) NOT NULL DEFAULT 0 COMMENT '学分',
	`total_hours` DECIMAL(3, 2) NOT NULL DEFAULT 0 COMMENT '总学时数',
	`institute` VARCHAR(32) NOT NULL COMMENT '开课学院',
	`first_class_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '首次开课时间, 默认为信息录入时间',
	`is_bilingual` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '是否双语授课, 默认0 非双语, 1 双语',
	`new_textbook_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '选用教材时间, 默认为信息录入时间',
	`soft_hard` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '软硬件课程, 默认0 软件, 1 硬件',
	`student_type` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '学生类型, 默认0 本科生, 1 专科生, 2 研究生',
	`admin_id` BIGINT(20) NOT NULL COMMENT '管理员编号',
	`remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='课程基本信息表';

CREATE TABLE `course_hours`(
	`id`BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`course_id` BIGINT(20) NOT NULL COMMENT '课程基本信息编码',
	`teacher_id` BIGINT(20) NOT NULL COMMENT '教师编码',
	`student_number` INT(11) NOT NULL DEFAULT 0 COMMENT '课程人数, 默认0',
	`total_capacity` INT(11) NOT NULL DEFAULT 0 COMMENT '课程总容量, 默认0',
	`selected_student` INT(11) NOT NULL DEFAULT 0 COMMENT '已选学生数, 默认0',
	`week_hours` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '周学时',
	`peroid` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '起止周',
	`classed` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '教学班组成',
	`teaching_hours` INT(11) NOT NULL DEFAULT 0 COMMENT '讲课学时, 默认0',
	`computer_hours` INT(11) NOT NULL DEFAULT 0 COMMENT '上机学时, 默认0',
	`experiment_hours` INT(11) NOT NULL DEFAULT 0 COMMENT '实验学时, 默认0',
	`selection_number` VARCHAR(32) NOT NULL COMMENT '选课编号',
	`students_institute` VARCHAR(32) NOT NULL DEFAULT '计算机学院' COMMENT '学生学院',
	`semester` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '学期, 默认1 第一学期, 2 第二学期',
	`school_year` INT(11) UNSIGNED NOT NULL COMMENT '学年',
	`admin_id` BIGINT(20) NOT NULL COMMENT '管理员编码',
	`primary_secondary` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '主讲辅讲标记, 默认0 主讲, 1 辅讲',
	`exp_number` INT(11) UNSIGNED DEFAULT 1 COMMENT '实验批数, 默认1',
	`exp_per_number` INT(11) UNSIGNED NULL DEFAULT 0 COMMENT '实验每批次人数, 默认0',
	`course_time` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '上课时间',
	`remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='课程课时具体信息表';

CREATE TABLE `course_hours_modify_record`(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`hours_id` BIGINT(20) NOT NULL COMMENT '课时信息编码',
	`apply_type` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '提出修改用户类型, 默认0 教师, 1 管理员',
	`apply_id` BIGINT(20) NOT NULL COMMENT '提出修改用户编码, teachar_id或者admin_id',
	`selected_student` INT(11) UNSIGNED NOT NULL COMMENT '已选学生人数, 非空',
	`course_cridet` DECIMAL(3, 2) NOT NULL COMMENT '学分, 非空',
	`total_hours` DECIMAL(3, 2) NOT NULL COMMENT '总学时数, 非空',
	`peroid` VARCHAR(32) NOT NULL COMMENT '起止周, 非空',
	`classed` VARCHAR(128) NOT NULL COMMENT '教学班组成, 非空',
	`modify_selected_student` INT(11) DEFAULT NULL COMMENT '修改已选学生人数',
	`modify_course_cridet` DECIMAL(3, 2) DEFAULT NULL COMMENT '修改学时',
	`modify_total_hours` DECIMAL(3, 2) DEFAULT NULL COMMENT '修改总学时数',
	`modify_peroid` VARCHAR(32) DEFAULT NULL COMMENT '修改起止周',
	`modify_classed` VARCHAR(128) DEFAULT NULL COMMENT '修改教学班组成',
	`admin_id` BIGINT(20) DEFAULT NULL COMMENT '处理的管理员编码',
	`check_time` TIMESTAMP NULL COMMENT '管理员处理时间',
	`check_result` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '管理员处理结果, 默认0 未处理, 1 通过, 2 驳回',
	`remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='课程课时修改记录表, 修改用户:教师/管理员';

CREATE TABLE `paper`(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`teacher_id` BIGINT(20) NOT NULL COMMENT '教师编码',
	`major_id` BIGINT(20) NOT NULL COMMENT '专业信息编码',
	`student_number` INT(11) NOT NULL DEFAULT 0 COMMENT '指导学生人数, 默认0',
	`semester` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '学期, 默认1 第一学期, 2 第二学期',
	`school_year` INT(11) UNSIGNED NOT NULL COMMENT '学年',
	`admin_id` BIGINT(20) DEFAULT NULL COMMENT '管理员编码',
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识, 0 无删除, 1 已删除',
	`remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='论文指导信息表';

CREATE TABLE `paper_modify_record`(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`paper_id` BIGINT(20) NOT NULL COMMENT '论文指导信息编码',
	`apply_type` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '提出修改用户类型, 默认0 教师, 1 管理员',
	`apply_id` BIGINT(20) NOT NULL COMMENT '提出修改用户编码, teachar_id或者admin_id',
	`major_id` BIGINT(20) NOT NULL COMMENT '专业信息编码',
	`student_number` INT(11) NOT NULL COMMENT '指导学生人数, 默认0',
	`semester` TINYINT(4) NOT NULL COMMENT '学期, 默认1 第一学期, 2 第二学期',
	`school_year` INT(11) UNSIGNED NOT NULL COMMENT '学年',
	`remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
	`is_deleted` TINYINT(4) NOT NULL COMMENT '逻辑删除标识, 0 无删除, 1 已删除',
	`modify_major_id` BIGINT(20) DEFAULT NULL COMMENT '修改专业信息编码',
	`modify_student_number` INT(11) DEFAULT NULL COMMENT '修改指导学生人数',
	`modify_semester` TINYINT(4) DEFAULT NULL COMMENT '修改学期',
	`modify_school_year` INT(11) UNSIGNED DEFAULT NULL COMMENT '修改学年',
	`modify_remark` VARCHAR(255) DEFAULT NULL COMMENT '修改备注',
	`modify_is_deleted` TINYINT(4) DEFAULT NULL COMMENT '修改逻辑删除标识',
	`admin_id` BIGINT(20) DEFAULT NULL COMMENT '处理的管理员编码',
	`check_time` TIMESTAMP NULL COMMENT '管理员处理时间',
	`check_result` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '管理员处理结果, 默认0 未处理, 1 通过, 2 驳回',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='论文指导修改记录表,修改用户:教师/管理员';

CREATE TABLE `intern`(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`teacher_id` BIGINT(20) NOT NULL COMMENT '教师编码',
	`normal_practice` INT(11) NOT NULL DEFAULT 0 COMMENT '师范实习带队人数, 默认0',
	`non_normal_practice` INT(11) NOT NULL DEFAULT 0 COMMENT '非师范实习带队人数, 默认0',
	`school_practice` INT(11) NOT NULL DEFAULT 0 COMMENT '校内实习带队人数, 默认0',
	`semester` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '学期, 默认1 第一学期, 2 第二学期',
	`school_year` INT(11) UNSIGNED NOT NULL COMMENT '学年',
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识, 0 无删除, 1 已删除',
	`remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='实习指导信息表';

CREATE TABLE `intern_modify_record`(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`intern_id` BIGINT(20) NOT NULL COMMENT '实习指导信息编码',
	`normal_practice` INT(11) NOT NULL COMMENT '师范实习带队人数',
	`non_normal_practice` INT(11) NOT NULL COMMENT '非师范实习带队人数',
	`school_practice` INT(11) NOT NULL COMMENT '校内实习带队人数',
	`semester` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '学期',
	`school_year` INT(11) UNSIGNED NOT NULL COMMENT '学年',
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识',
	`modify_normal_practice` INT(11) DEFAULT NULL COMMENT '修改师范实习带队人数',
	`modify_non_normal_practice` INT(11) DEFAULT NULL COMMENT '修改非师范实习带队人数',
	`modify_school_practice` INT(11) DEFAULT NULL COMMENT '修改校内实习带队人数',
	`modify_semester` TINYINT(4) DEFAULT NULL COMMENT '修改学期',
	`modify_school_year` INT(11) UNSIGNED DEFAULT NULL COMMENT '修改学年',
	`modify_is_deleted` TINYINT(4) DEFAULT NULL COMMENT '修改逻辑删除标识',
	`apply_type` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '提出修改用户类型, 默认0 教师, 1 管理员',
	`apply_id` BIGINT(20) NOT NULL COMMENT '提出修改用户编码, teachar_id或者admin_id',
	`admin_id` BIGINT(20) DEFAULT NULL COMMENT '处理的管理员编码',
	`check_time` TIMESTAMP NULL COMMENT '管理员处理时间',
	`check_result` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '管理员处理结果, 默认0 未处理, 1 通过, 2 驳回',
	`remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='实习指导修改记录表, 修改用户:教师/管理员';

CREATE TABLE `course_score`(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`teacher_id` BIGINT(20) NOT NULL COMMENT '教师编码',
	`course_hours_id` BIGINT(20) NOT NULL COMMENT '课程课时具体信息编码',
	`c1_value` DECIMAL(5, 3) NOT NULL COMMENT 'c1权值',
	`c2_value` DECIMAL(5, 3) NOT NULL COMMENT 'c2权值',
	`c3_value` DECIMAL(5, 3) NOT NULL COMMENT 'c3权值',
	`c4_value` DECIMAL(5, 3) NOT NULL COMMENT 'c4权值',
	`c5_value` DECIMAL(5, 3) NOT NULL COMMENT 'c5权值',
	`c6_value` DECIMAL(5, 3) NOT NULL COMMENT 'c6权值',
	`c7_value` DECIMAL(5, 3) NOT NULL COMMENT 'c7权值',
	`c8_value` DECIMAL(5, 3) NOT NULL COMMENT 'c8权值',
	`total` DECIMAL(5, 3) NOT NULL COMMENT '授课总分',
	`semester` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '学期, 默认1 第一学期, 2 第二学期',
	`school_year` INT(11) UNSIGNED NOT NULL COMMENT '学年',
	`admin_id` BIGINT(20) DEFAULT NULL COMMENT '处理的管理员编码',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识',
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='课程工作量统计表';

CREATE TABLE `paper_score`(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`teacher_id` BIGINT(20) NOT NULL COMMENT '教师编码',
	`paper_id` BIGINT(20) NOT NULL COMMENT '论文指导信息编码',
	`c9_value` DECIMAL(5, 3) NOT NULL COMMENT 'c9权值',
	`c10_value` DECIMAL(5, 3) NOT NULL COMMENT 'c10权值',
	`c11_value` DECIMAL(5, 3) NOT NULL COMMENT 'c11权值',
	`c12_value` DECIMAL(5, 3) NOT NULL COMMENT 'c12权值',
	`c13_value` DECIMAL(5, 3) NOT NULL COMMENT 'c13权值',
	`total` DECIMAL(5, 3) NOT NULL COMMENT '论文指导总分',
	`semester` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '学期, 默认1 第一学期, 2 第二学期',
	`school_year` INT(11) UNSIGNED NOT NULL COMMENT '学年',
	`admin_id` BIGINT(20) DEFAULT NULL COMMENT '处理的管理员编码',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识',
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='论文指导工作量统计表';

CREATE TABLE `intern_score`(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`teacher_id` BIGINT(20) NOT NULL COMMENT '教师编码',
	`intern_id` BIGINT(20) NOT NULL COMMENT '实习指信息编码',
	`c14_value` DECIMAL(5, 3) NOT NULL COMMENT 'c14权值',
	`c15_value` DECIMAL(5, 3) NOT NULL COMMENT 'c15权值',
	`total` DECIMAL(5, 3) NOT NULL COMMENT '实习指导总分',
	`semester` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '学期, 默认1 第一学期, 2 第二学期',
	`school_year` INT(11) UNSIGNED NOT NULL COMMENT '学年',
	`admin_id` BIGINT(20) DEFAULT NULL COMMENT '处理的管理员编码',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识',
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='实习带队工作量统计表';

CREATE TABLE `assessment`(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`teacher_id` BIGINT(20) NOT NULL COMMENT '教师编码',
	`semester` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '学期, 默认1 第一学期, 2 第二学期',
	`school_year` INT(11) UNSIGNED NOT NULL COMMENT '学年',
	`course_quality` DECIMAL(10, 3) NOT NULL COMMENT '授课总分',
	`paper_quality` DECIMAL(10, 3) NOT NULL COMMENT '论文指导总分',
	`intern_quality` DECIMAL(10, 3) NOT NULL COMMENT '实习指导总分',
	`other_quality` DECIMAL(10, 3) NOT NULL DEFAULT 0 COMMENT '其他总分',
	`total_quality` DECIMAL(10, 3) NOT NULL COMMENT '总分',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='教师绩效核算记录表';

CREATE TABLE `user_log`(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  	`ip` varchar(36) DEFAULT NULL COMMENT '远程IP地址',
	`user_id` BIGINT(20) NOT NULL COMMENT '用户编码',
	`user_type` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '用户类型, 默认0 教师, 1 管理员',
  	`url` varchar(255) DEFAULT NULL COMMENT '请求路由',
  	`param` varchar(2000) DEFAULT NULL COMMENT '请求参数',
  	`result_code` varchar(10) DEFAULT NULL COMMENT '操作结果码',
  	`result_msg` varchar(200) DEFAULT NULL COMMENT '操作结果响应信息',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用户访问记录表';

# 管理员
DROP USER IF EXISTS `tpas_admin`@`localhost`;
CREATE USER `tpas_admin`@`localhost` IDENTIFIED BY 'AaBb2020!';
GRANT ALL ON `teacher_performance`.`*` TO `tpas_admin`@`localhost`;

# ----------------------------------------SQL v1.0.0----------------------------------------

