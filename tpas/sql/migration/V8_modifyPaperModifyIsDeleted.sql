# 修改论文记录修改表的is_deleted字段的默认值为0
alter table paper_modify_record modify column is_deleted TINYINT(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识, 0 无删除, 1 已删除'