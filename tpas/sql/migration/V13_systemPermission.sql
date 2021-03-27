-- ---------------------系统权限表--------------------------------
use teacher_performance;

CREATE TABLE `system_permission`(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`permission_name` VARCHAR(100) DEFAULT NULL COMMENT '权限名称',
	`permission_key` VARCHAR(100) DEFAULT NULL COMMENT '权限key',
	`value` VARCHAR(200) DEFAULT NULL COMMENT '菜单值',
	`url_path` VARCHAR(200) DEFAULT NULL COMMENT '控制路径，api路径',
	`control_type` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '控制类型，1列表，2查询框，3按钮，4其他',
	`filed_name` VARCHAR(200) DEFAULT NULL COMMENT '控制类型为2时表示查询的字段',
	`remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '账号逻辑删除标识, 0 无删除, 1 已删除',
	UNIQUE KEY `UNIQUE_INDEX` (`permission_key`, `filed_name`) USING BTREE,
	KEY `VALUE_INDEX` (`value`) USING BTREE,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='系统菜单详细权限表';

INSERT INTO `system_permission`(`permission_name`, `permission_key`, `value`, `url_path`, `control_type`, `filed_name`, `remark`)VALUES
('导入课时信息按钮', 'importCourseHour-Button', 'AssessmentInfo-CourseHour', '', 3, '', '导入课时信息文件按钮'),
('导出课时信息按钮', 'exportCourseHour-Button', 'AssessmentInfo-CourseHour', '', 3, '', '导出课时信息文件按钮'),
('下载课时模板文件按钮', 'downloadCourseHourTemplate-Button', 'AssessmentInfo-CourseHour', '', 3, '', '下载课时模板文件按钮'),
('导入实习指导信息按钮', 'importIntern-Button', 'AssessmentInfo-Intern', '', 3, '', '导入实习指导信息文件按钮'),
('导出实习指导信息按钮', 'exportIntern-Button', 'AssessmentInfo-Intern', '', 3, '', '导出实习指导信息文件按钮'),
('下载实习指导模板文件按钮', 'downloadInternTemplate-Button', 'AssessmentInfo-Intern', '', 3, '', '下载实习指导模板文件按钮'),
('导入论文指导信息按钮', 'importPaper-Button', 'AssessmentInfo-Paper', '', 3, '', '导入论文指导信息文件按钮'),
('导出论文指导信息按钮', 'exportPaper-Button', 'AssessmentInfo-Paper', '', 3, '', '导出论文指导信息文件按钮'),
('下载论文指导模板文件按钮', 'downloadPaperTemplate-Button', 'AssessmentInfo-Paper', '', 3, '', '下载论文指导模板文件按钮');

CREATE TABLE `system_role_permission_ref`(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`permission_key` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '权限key',
	`role_name` varchar(100) NOT NULL DEFAULT '' COMMENT '角色名称',
	`value` VARCHAR(200) DEFAULT NULL COMMENT '菜单值',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '账号逻辑删除标识, 0 无删除, 1 已删除',
	UNIQUE KEY `UNIQUE_INDEX` (`role_name`, `value`, `permission_key`) USING BTREE,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='系统角色权限关系表';

-- ---------------------系统权限表--------------------------------
