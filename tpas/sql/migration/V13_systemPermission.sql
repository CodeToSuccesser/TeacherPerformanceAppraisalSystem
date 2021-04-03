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
('导入论文指导信息按钮', 'importPaper-Button', 'AssessmentInfo-Paper', '', 3, ''Intern, '导入论文指导信息文件按钮'),
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

-- ---------------------考核分数查询权限---------------------------
INSERT INTO `system_permission`(`permission_name`, `permission_key`, `value`, `url_path`, `control_type`, `filed_name`, `remark`)VALUES
('考核成绩列表', 'queryAssessList-List', 'Assess-Analysis', '/score/queryAssessList', 1, '', '考核成绩列表'),
('考核成绩搜索教师编码', 'queryAssessListSearch-teacherCode', 'Assess-Analysis', '/score/queryAssessList', 2, 'teacherCode', '考核成绩搜索参数教师信息'),
('查询授课考核明细列表', 'courseQueryScoreList-List', 'Assess-Score', '/score/course/queryScoreList', 1, '', '查询授课考核明细列表'),
('实习考核明细列表', 'internQueryScoreList-List', 'Assess-Score', '/score/intern/queryScoreList', 1, '', '查询实习考核明细列表'),
('论文考核明细列表', 'paperQueryScoreList-List', 'Assess-Score', '/score/paper/queryScoreList', 1, '', '查询论文考核明细列表'),
('授课考核明细搜索参数教师信息', 'courseQueryScoreList-teacherCode', 'Assess-Score', '/score/course/queryScoreList', 2, 'teacherCode', '查询授课考核明细搜索参数教师信息'),
('实习考核明细搜索参数教师信息', 'internQueryScoreList-teacherCode', 'Assess-Score', '/score/intern/queryScoreList', 2, 'teacherCode', '查询实习考核明细搜索参数教师信息'),
('论文考核明细搜索参数教师信息', 'paperQueryScoreList-teacherCode', 'Assess-Score', '/score/paper/queryScoreList', 2, 'teacherCode', '查询论文考核明细搜索参数教师信息');


-- 考核触发权限
INSERT INTO `system_permission`(`permission_name`, `permission_key`, `value`, `url_path`, `control_type`, `filed_name`, `remark`)VALUES
('授课考核触发按钮', 'courseCalculateAssess-button', 'AssessmentInfo-CourseHour', '/params/info/calculateAssess', 3, '', '授课考核触发按钮'),
('实习考核触发按钮', 'internCalculateAssess-button', 'AssessmentInfo-Intern', '/params/info/calculateAssess', 3, '', '实习考核触发按钮'),
('论文考核触发按钮', 'paperCalculateAssess-button', 'AssessmentInfo-Paper', '/params/info/calculateAssess', 3, '', '论文考核触发按钮');



DELETE FROM system_permission WHERE permission_key = 'getCourseHoursSearch-teacherId';
UPDATE system_role_permission_ref set permission_key = 'getCourseHoursSearch-teacherCode' WHERE permission_key = 'getCourseHoursSearch-teacherId';
INSERT INTO system_permission (permission_name,permission_key,value,url_path,control_type,filed_name,remark,update_time,create_time,is_deleted) VALUES
('查询课时记录teacherCode','getCourseHoursSearch-teacherCode','AssessmentInfo-CourseHour','/course/hours/getCourseHours',2,'teacherCode','查询课时记录teacherCode','2021-04-04 01:27:23.0','2021-03-26 18:08:24.0',0);

INSERT INTO system_permission (permission_name,permission_key,value,url_path,control_type,filed_name,remark) VALUES
('查询论文记录teacherCode','getPaperSearch-teacherCode','AssessmentInfo-Paper','/paper/info/getPaperInfo',2,'teacherCode','查询论文记录teacherCode'),
('查询实习记录teacherCode','getInternSearch-teacherCode','AssessmentInfo-Intern','/intern/info/getInternInfo',2,'teacherCode','查询实习记录teacherCode');
-- ---------------------考核分数查询权限---------------------------