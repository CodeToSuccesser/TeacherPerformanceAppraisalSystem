# 增加课程基本信息编码字段
ALTER TABLE course_base ADD course_code VARCHAR(32) NOT NULL COMMENT '课程编码, 唯一, 格式为: 21G39280-2938';
ALTER TABLE course_base ADD UNIQUE cb_cc_uniq('course_code');
ALTER TABLE course_hours MODIFY COLUMN school_year VARCHAR(32) NOT NULL COMMENT '学年';