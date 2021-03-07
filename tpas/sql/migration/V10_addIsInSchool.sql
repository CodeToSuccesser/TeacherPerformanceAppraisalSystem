# 增加实习带队记录师范生为校内记录或校外记录
ALTER TABLE intern ADD is_in_school TINYINT(4) NOT NULL DEFAULT 0 COMMENT '对于师范实习记录，标注为校内或校外实习，校外-0，校内-1';
