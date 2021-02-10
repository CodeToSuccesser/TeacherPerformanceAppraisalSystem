# 更改所有schoolYear字段为VARCHAR

ALTER TABLE paper modify column school_year VARCHAR(32) NOT NULL COMMENT '学年';

ALTER TABLE paper_modify_record modify column school_year VARCHAR(32) NOT NULL COMMENT '学年';

ALTER TABLE paper_modify_record modify column modify_school_year VARCHAR(32) NOT NULL COMMENT '学年';

ALTER TABLE intern modify column school_year VARCHAR(32) NOT NULL COMMENT '学年';

ALTER TABLE intern_modify_record modify column school_year VARCHAR(32) NOT NULL COMMENT '学年';

ALTER TABLE intern_modify_record modify column modify_school_year VARCHAR(32) NOT NULL COMMENT '学年';

ALTER TABLE course_score modify column school_year VARCHAR(32) NOT NULL COMMENT '学年';

ALTER TABLE paper_score modify school_year school_year VARCHAR(32) NOT NULL COMMENT '学年';

ALTER TABLE intern_score modify school_year school_year VARCHAR(32) NOT NULL COMMENT '学年';

ALTER TABLE assessment modify school_year school_year VARCHAR(32) NOT NULL COMMENT '学年';
