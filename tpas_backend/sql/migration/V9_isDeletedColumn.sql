-- 优化统一逻辑删除标志

alter table paper_modify_record drop column modify_is_deleted;

alter table intern_modify_record drop column modify_is_deleted;

alter table course_base add column is_deleted TINYINT(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识, 0 无删除, 1 已删除';

alter table course_hours_modify_record add column is_deleted TINYINT(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识, 0 无删除, 1 已删除';
