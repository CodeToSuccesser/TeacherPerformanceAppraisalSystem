-- ----------------------系统管理重构--------------------------
use `teacher_performance`;
ALTER TABLE `params` DROP COLUMN `c_opction`;
ALTER TABLE `params` DROP COLUMN `value`;
ALTER TABLE `params` ADD COLUMN `c_name` VARCHAR(60) DEFAULT NULL COMMENT '权值名称';

CREATE TABLE `rule_setting`(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`c_type` TINYINT(4) NOT NULL COMMENT '权值类型, 1 授课, 2 指导论文',
	`value_name` VARCHAR(60) nOT NULL DEFAULT '' COMMENT '规则限制字段名称',
	`rule_type` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '规则类型, 1 值区分, 2 区间区分',
	`left_value` DECIMAL(9, 3) DEFAULT NULL COMMENT '规则类型为值区分时, 表示字段值, 规则类型为区间区分时, 表示左区间',
	`right_value` DECIMAL(9, 3) DEFAULT NULL COMMENT '规则类型为值区分时, 表示字段值, 规则类型为区间区分时, 表示右区间',
	`remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识, 0 无删除, 1 已删除',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='权值字段规则表';

INSERT INTO `rule_setting`(`id`, `c_type`, `value_name`, `rule_type`, `left_value`, `right_value`, `remark`)VALUES
(1, 1, 'computer_hours', 1, 0, 0, '理论课-上机学时0'),
(2, 1, 'experiment_hours', 1, 0, 0, '理论课-实验学时0'),
(3, 1, 'experiment_hours', 2, 0.1, null, '课程实验'),
(4, 1, 'course_type', 1, 3, 3, '实验课程'),
(5, 1, 'student_type', 2, 0, 1, '全日制本、专科授课'),
(6, 1, 'student_type', 1, 2, 2, '全日制研究生授课'),
(7, 1, 'student_type', 1, 3, 3, '研究生课程班授课，不处理'),
(8, 1, 'student_number', 2, 0, 60, '学生人数[0, 60]'),
(9, 1, 'student_number', 2, 61, 100, '学生人数[61, 100]'),
(10, 1, 'student_number', 2, 101, 150, '学生人数[101, 150]'),
(11, 1, 'student_number', 2, 151, null, '学生人数[151, ?]'),
(12, 1, 'first_class_time', 1, 0, 0, '首次开课，未设置'),
(13, 1, 'is_bilingual', 1, 1, 1, '双语课程'),
(14, 1, 'is_bilingual', 1, 0, 0, '非双语课程'),
(15, 1, 'new_textbook_time', 1, 0, 0, '新教材，未设置'),
(16, 1, 'primary_secondary', 1, 0, 0, '主讲'),
(17, 1, 'primary_secondary', 1, 1, 1, '辅讲'),
(18, 1, 'soft_hard', 1, 0, 0, '软件'),
(19, 1, 'soft_hard', 1, 1, 1, '硬件'),
(20, 1, 'student_number', 2, 0, 40, '学生人数[0, 40]'),
(21, 1, 'student_number', 2, 41, 60, '学生人数[41, 60]'),
(22, 1, 'student_number', 2, 0, 30, '学生人数[0, 30]'),
(23, 1, 'student_number', 2, 31, 60, '学生人数[31, 60]'),
(24, 1, 'student_number', 2, 61, 90, '学生人数[61, 90]'),
(25, 1, '', 1, 0, 0, '石牌校区，未设置'),
(26, 1, '', 1, 0, 0, '南海校区，未设置');



CREATE TABLE `params_rules_setting`(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`c_num` TINYINT(4) NOT NULL DEFAULT 1 COMMENT 'Cn的n值, 1 表示C1, range[1, 13]',
	`c_option` TINYINT(4) NOT NULL DEFAULT 1 COMMENT 'm表示第m个Cn',
	`rules_setting_ids` VARCHAR(60) NOT NULL DEFAULT '' COMMENT '权值字段规则, 由逗号拼接, 如: 1,3',
	`value_type` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '取值类型, 1 取字段值, 2 取参数权值',
	`column_name` VARCHAR(60) DEFAULT NULL COMMENT '字段值, 如student_number, 取值类型为1时有效',
	`param_value` DECIMAL(9, 3) DEFAULT NULL COMMENT '参数权值, 取值类型为2时有效',
	`remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识, 0 无删除, 1 已删除',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	UNIQUE INDEX `C_NUM_AND_C_OPTION_INDEX`(`c_num`, `c_option`) USING BTREE,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='权值字段取值表';

INSERT INTO `params_rules_setting`(`id`, `c_num`, `c_option`, `rules_setting_ids`, `value_type`, `column_name`, `param_value`, `remark`)VALUES
(1, 1, 1, '1,2', 1, 'teaching_hours', 0, '理论课C1, 理论课学时'),
(2, 1, 2, '3', 1, 'experiment_hours', 0, '课程实验C1, 实验学时'),
(3, 1, 3, '4', 1, 'experiment_hours', 0, '实验课程C1, 实验学时'),
(4, 2, 1, '5', 2, '', 0, '全日制本、专科授课C2'),
(5, 2, 2, '6', 2, '', 0.2, '全日制研究生授课C2'),
(6, 2, 3, '7', 2, '', 0.3, '研究生课程班授课C2'),
(7, 3, 1, '1,2,8', 2, '', 0, '理论课上课学生人数[0, 60]C3'),
(8, 3, 2, '1,2,9', 2, '', 0.1, '理论课上课学生人数[61,100]C3'),
(9, 3, 3, '1,2,10', 2, '', 0.2, '理论课上课学生人数[101,150]C3'),
(10, 3, 4, '1,2,11', 2, '', 0.3, '理论课上课学生人数[150, -)C3'),
(11, 4, 1, '12,14', 2, '', 0.3, '讲授新课或带新实验C4'),
(12, 4, 2, '12,13', 2, '', 0.5, '双语新课C4'),
(13, 4, 3, '15', 2, '', 0.2, '换新教材或新实验指导书C4'),
(14, 4, 4, '', 2, '', 0, '默认'),
(15, 5, 1, '4,16', 2, '', 0.8, '实验课程一主一辅主讲C5'),
(16, 5, 2, '4,17', 2, '', 0.6, '实验课程一主一辅辅讲C5'),
(17, 5, 3, '3', 2, '', 0.8, '课程实验C5'),
(18, 6, 1, '4', 1, 'exp_number', 0, '实验课程实验的实际批次C6'),
(19, 6, 2, '3', 2, '', 1, '课程实验C6'),
(20, 7, 1, '4,18,20', 2, '', 0, '软件的实验课程一位老师带实验人数[0,40]C7'),
(21, 7, 2, '4,18,21', 2, '', 0.2, '软件的实验课程一位老师带实验人数[41,60]C7'),
(22, 7, 3, '4,18,9', 2, '', 0, '软件的实验课程两位老师（一主一辅）带实验人数[61,100]C7'),
(23, 7, 4, '4,19,22', 2, '', 0, '硬件的实验课程一位老师带实验人数[0,30]C7'),
(24, 7, 5, '4,19,23', 2, '', 0, '硬件的实验课程两位老师（一主一辅）带实验人数[31,60]C7'),
(25, 7, 6, '3,8', 2, '', 0, '课程实验实验人数[0,60]C7'),
(26, 7, 7, '3,24', 2, '', 0.2, '课程实验实验人数实验人数[61,90]C7'),
(27, 8, 1, '25', 2, '', 1, '石牌校区上课'),
(28, 8, 2, '26', 2, '', 1.5, '南海校区上课'),
(29, 8, 3, '25', 2, '', 1, '默认'),
(30, 9, 1, '', 2, '', 2, '期末复习绩效加数'),
(31, 10, 1, '', 2, '', 0.05, '期末复习绩效倍数'),
(32, 11, 1, '', 1, 'student_number', 0, '学生人数'),
(33, 12, 1, '', 2, '', 5, '指导全日制本科生论文倍数');


CREATE TABLE `assess_rule`(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`c_type` TINYINT(4) NOT NULL COMMENT '权值类型, 1 授课, 2 指导论文, 3 其他',
	`assess_detail` VARCHAR(60) NOT NULL DEFAULT 0 COMMENT '绩效计算公式, 如研究生、本、专科理论课: {1}*{8}*(1+{2}+{3}+{4})',
	`remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识, 0 无删除, 1 已删除',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	INDEX `C_TYPE_INDEX`(`c_type`) USING BTREE,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='绩效核算规则表';

INSERT INTO `assess_rule` (`c_type`, `assess_detail`, `remark`) VALUES
(1, 'C1*C8*(1+C2+C3+C4)', '研究生、本、专科理论课'),
(1, 'C1*C5*C6*(1+C4+C7)', '实验课程'),
(1, 'C1*C5*C6*(1+C4+C7)', '课程实验'),
(1, 'C9+C10*C11', '期末复习绩效'),
(2, 'C11*C12', '指导全日制本科生论文');
-- ----------------------系统管理重构--------------------------