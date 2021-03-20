-- 增加任务分配，其他绩效工作，公示信息表内容

CREATE TABLE `task_permissions`(
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键', 
    `publisher_role` VARCHAR(100) NOT NULL COMMENT '发布者角色',
    `receiver_roles` VARCHAR(255) NOT NULL COMMENT '任务接受者角色，如有多个，逗号分隔',
    `is_deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '账号逻辑删除标识, 0 无删除, 1 已删除',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='任务下发权限表';

CREATE TABLE `task`(
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键', 
    `publisher_id` BIGINT(20) NOT NULL COMMENT '任务创建/下发者用户id',
    `receiver_id` BIGINT(20) NOT NULL COMMENT '任务接受者用户id',
    `title` VARCHAR(255) NOT NULL COMMENT '任务标题',
    `content` VARCHAR(1000) NOT NULL COMMENT '任务内容描述',
    `picture_url` VARCHAR(255) DEFAULT NULL COMMENT '任务说明图片url, 可为空',
    `start_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '任务起始时间',
    `end_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '任务截止时间',
    `score` INT(11) NOT NULL DEFAULT 0 COMMENT '任务完成评分，百分制',
    `state` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '任务状态： 0-进行中，1-已截止/已到期',
    `complete_degree` INT(11) NOT NULL DEFAULT 0 COMMENT '任务完成率反馈，百分制',
    `feedback_content` VARCHAR(1000) DEFAULT NULL COMMENT '任务完成情况反馈',
    `is_deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '账号逻辑删除标识, 0 无删除, 1 已删除',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='任务下发内容表';


CREATE TABLE `publicity`(
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键', 
    `publisher_id` BIGINT(20) NOT NULL COMMENT '公示发布者id',
    `title` VARCHAR(255) NOT NULL COMMENT '公示标题',
    `content` VARCHAR(1000) NOT NULL COMMENT '公示内容描述',
    `picture_url` VARCHAR(255) DEFAULT NULL COMMENT '公示说明图片url, 可为空',
    `is_deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '账号逻辑删除标识, 0 无删除, 1 已删除',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='公示信息表';

CREATE TABLE `other_performance`(
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键', 
    `user_id` BIGINT(20) NOT NULL COMMENT '教师id，绩效工作所属用户id',
    `title` VARCHAR(255) NOT NULL COMMENT '任务标题',
    `content` VARCHAR(1000) NOT NULL COMMENT '任务内容描述',
    `score` INT(11) NOT NULL DEFAULT 0 COMMENT '任务完成评分，百分制',
    `semester` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '学期, 默认1 第一学期, 2 第二学期',
    `school_year` VARCHAR(32) NOT NULL COMMENT '学年',
    `admin_id` BIGINT(20) NOT NULL COMMENT '管理员编码',
    `remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
    `is_deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '账号逻辑删除标识, 0 无删除, 1 已删除',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据最新操作时间',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='其他教务工作内容';
