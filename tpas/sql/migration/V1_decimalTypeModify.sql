# decimal字段变更

alter table params modify column value DECIMAL(9, 3);

alter table course_base modify column course_cridet DECIMAL(9, 3);
alter table course_base modify column total_hours DECIMAL(9, 3);

alter table course_hours_modify_record modify column course_cridet DECIMAL(6, 3);
alter table course_hours_modify_record modify column total_hours DECIMAL(6, 3);
alter table course_hours_modify_record modify column modify_course_cridet DECIMAL(6, 3);
alter table course_hours_modify_record modify column modify_total_hours DECIMAL(6, 3);

alter table course_score modify column c1_value DECIMAL(9, 3);
alter table course_score modify column c2_value DECIMAL(9, 3);
alter table course_score modify column c3_value DECIMAL(9, 3);
alter table course_score modify column c4_value DECIMAL(9, 3);
alter table course_score modify column c5_value DECIMAL(9, 3);
alter table course_score modify column c6_value DECIMAL(9, 3);
alter table course_score modify column c7_value DECIMAL(9, 3);
alter table course_score modify column c8_value DECIMAL(9, 3);
alter table course_score modify column total DECIMAL(9, 3);

alter table paper_score modify column c9_value DECIMAL(9, 3);
alter table paper_score modify column c10_value DECIMAL(9, 3);
alter table paper_score modify column c11_value DECIMAL(9, 3);
alter table paper_score modify column c12_value DECIMAL(9, 3);
alter table paper_score modify column c13_value DECIMAL(9, 3);
alter table paper_score modify column total DECIMAL(9, 3);

alter table intern_score modify column c14_value DECIMAL(9, 3);
alter table intern_score modify column c15_value DECIMAL(9, 3);
alter table intern_score modify column total DECIMAL(9, 3);

alter table assessment modify column course_quality DECIMAL(14, 3);
alter table assessment modify column paper_quality DECIMAL(14, 3);
alter table assessment modify column intern_quality DECIMAL(14, 3);
alter table assessment modify column other_quality DECIMAL(14, 3);
alter table assessment modify column total_quality DECIMAL(14, 3);
