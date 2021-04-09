-- 更改配置，适配group by 非全值
SELECT @@GLOBAL.sql_mode;
SET @@GLOBAL.sql_mode=(SELECT REPLACE(@@GLOBAL.sql_mode,'ONLY_FULL_GROUP_BY,',''))