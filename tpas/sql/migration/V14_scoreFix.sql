alter table assessment add column is_deleted TINYINT(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识, 0 无删除, 1 已删除';
