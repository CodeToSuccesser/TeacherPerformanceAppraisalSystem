-- ----------------------系统管理目录调整-----------------------
use teacher_performance;

delete from system_menu;

INSERT INTO `system_menu`(`value`, `label`, `path`, `icon_type`, `level`, `parent_value`, `full_value`)VALUES
('AssessmentInfo', '教务信息', '/AssessmentInfo', 'el-icon-s-help', 0, '', 'AssessmentInfo'),
('AssessmentInfo-CourseHour', '课时信息', '/CourseHour', 'table', 1, 'AssessmentInfo', 'AssessmentInfo|AssessmentInfo-CourseHour'),
('AssessmentInfo-Paper', '论文指导信息', '/Paper', 'education', 1, 'AssessmentInfo', 'AssessmentInfo|AssessmentInfo-Paper'),
('AssessmentInfo-Intern', '实习带队信息', '/Intern', 'tree', 1, 'AssessmentInfo', 'AssessmentInfo|AssessmentInfo-Intern'),
('AssessmentInfo-Task', '任务待办', '/Task', 'el-icon-date', 1, 'AssessmentInfo', 'AssessmentInfo|AssessmentInfo-Task'),
('AssessmentInfo-Others', '其他', '/Others', 'el-icon-files', 1, 'AssessmentInfo', 'AssessmentInfo|AssessmentInfo-Others'),
('AffairManage', '教务管理', '/AffairManage', 'el-icon-date', 0, '', 'AffairManage'),
('AffairManage-TaskManage', '任务分配', '/TaskManage', 'el-icon-date', 1, 'AffairManage', 'AffairManage|AffairManage-TaskManage'),
('AffairManage-AduitManage', '修改审批', '/AduitManage', 'el-icon-document-copy', 1, 'AffairManage', 'AffairManage|AffairManage-AduitManage'),
('Modify', '修改申请', '/Modify', 'nested', 0, '', 'Modify'),
('Modify-Modifying', '进行中', '/Modifying', 'el-icon-document-remove', 1, 'Modify', 'Modify|Modify-Modifying'),
('Modify-Finished', '已完成', '/Finished', 'el-icon-document-checked', 1, 'Modify', 'Modify|Modify-Finished'),
('Assess', '考核结果', '/Assess', 'el-icon-data-line', 0, 'Assess', 'Assess'),
('Assess-Score', '考核成绩', '/Score', 'el-icon-finished', 1, 'Assess', 'Assess|Assess-Score'),
('Assess-Analysis', '汇总分析', '/Analysis', 'el-icon-data-line', 1, 'Assess', 'Assess|Assess-Analysis'),
('SystemManage', '系统管理', '/SystemManage', 'el-icon-set-up', 0, '', 'SystemManage'),
('SystemManage-RulesManage', '绩效规则管理', '/RulesManage', 'el-icon-odometer', 1, 'SystemManage', 'SystemManage|SystemManage-'),
('SystemManage-CourseManage', '课程管理', '/CourseManage', 'el-icon-document', 1, 'SystemManage', 'SystemManage|SystemManage-CourseManage'),
('SystemManage-MajorManage', '专业管理', '/MajorManage', 'el-icon-office-building', 1, 'SystemManage', 'SystemManage|SystemManage-MajorManage'),
('SystemManage-PublicityManage', '公示管理', '/PublicityManage', 'el-icon-chat-line-square', 1, 'SystemManage', 'SystemManage|SystemManage-PublicityManage'),
('UserAndRolesManage', '用户及权限管理', '/UserAndRolesManage', 'user', 0, '', 'UserAndRolesManage'),
('UserAndRolesManage-UserManage', '用户管理', '/UserManage', 'user', 1, 'UserAndRolesManage', 'UserAndRolesManage|UserAndRolesManage-UserManage'),
('UserAndRolesManage-RolesManage', '角色权限管理', '/RolesManage', 'el-icon-unlock', 1, 'UserAndRolesManage', 'UserAndRolesManage|UserAndRolesManage-RolesManage');

delete from system_role;

INSERT INTO `system_role`(`name`, `menus_value`) VALUES
('管理员角色', 'AffairManage,AffairManage-TaskManage,AffairManage-AduitManage,SystemManage,SystemManage-RulesManage,SystemManage-CourseManage,SystemManage-MajorManage,SystemManage-PublicityManage,UserAndRolesManage,UserAndRolesManage-UserManage,UserAndRolesManage-RolesManage'),
('教师角色', 'AssessmentInfo,AssessmentInfo-CourseHour,AssessmentInfo-Paper,AssessmentInfo-Intern,AssessmentInfo-Task,AssessmentInfo-Others,Modify,Modify-Modifying,Modify-Finished,Assess,Assess-Score,Assess-Analysis'),
('全菜单', 'AssessmentInfo,AssessmentInfo-CourseHour,AssessmentInfo-Paper,AssessmentInfo-Intern,AssessmentInfo-Task,AssessmentInfo-Others,Modify,Modify-Modifying,Modify-Finished,Assess,Assess-Score,Assess-Analysis,AffairManage,AffairManage-TaskManage,AffairManage-AduitManage,SystemManage,SystemManage-RulesManage,SystemManage-CourseManage,SystemManage-MajorManage,SystemManage-PublicityManage,UserAndRolesManage,UserAndRolesManage-UserManage,UserAndRolesManage-RolesManage'),
('无', '');
-- ----------------------系统管理目录调整-----------------------