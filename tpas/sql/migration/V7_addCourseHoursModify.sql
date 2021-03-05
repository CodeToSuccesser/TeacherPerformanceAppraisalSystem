-- 课时信息修改记录表增加讲课、上机、实验学时修改记录字段
ALTER TABLE course_hours_modify_record ADD teaching_hours INT(11) NOT NULL DEFAULT 0 COMMENT '讲课学时, 默认0';

ALTER TABLE course_hours_modify_record ADD modify_teaching_hours INT(11) NOT NULL DEFAULT 0 COMMENT '修改讲课学时, 默认0';

ALTER TABLE course_hours_modify_record ADD computer_hours INT(11) NOT NULL DEFAULT 0 COMMENT '上机学时, 默认0';

ALTER TABLE course_hours_modify_record ADD modify_computer_hours INT(11) NOT NULL DEFAULT 0 COMMENT '修改上机学时, 默认0';

ALTER TABLE course_hours_modify_record ADD experiment_hours INT(11) NOT NULL DEFAULT 0 COMMENT '实验学时, 默认0';

ALTER TABLE course_hours_modify_record ADD modify_experiment_hours INT(11) NOT NULL DEFAULT 0 COMMENT '修改实验学时, 默认0';
