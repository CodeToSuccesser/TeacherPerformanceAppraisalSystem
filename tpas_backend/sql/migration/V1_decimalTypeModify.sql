# decimal字段变更

alter table params modify(value, DECIMAL(9, 3));

alter table course_base modify(course_cridet, DECIMAL(9, 3));
alter table course_base modify(total_hours, DECIMAL(9, 3));

alter table course_hours_modify_record modify(course_cridet, DECIMAL(6, 3));
alter table course_hours_modify_record modify(total_hours, DECIMAL(6, 3));
alter table course_hours_modify_record modify(modify_course_cridet, DECIMAL(6, 3));
alter table course_hours_modify_record modify(modify_total_hours, DECIMAL(6, 3));

alter table course_score modify(c1_value, DECIMAL(9, 3));
alter table course_score modify(c2_value, DECIMAL(9, 3));
alter table course_score modify(c3_value, DECIMAL(9, 3));
alter table course_score modify(c4_value, DECIMAL(9, 3));
alter table course_score modify(c5_value, DECIMAL(9, 3));
alter table course_score modify(c6_value, DECIMAL(9, 3));
alter table course_score modify(c7_value, DECIMAL(9, 3));
alter table course_score modify(c8_value, DECIMAL(9, 3));
alter table course_score modify(total, DECIMAL(9, 3));

alter table paper_score modify(c9_value, DECIMAL(9, 3));
alter table paper_score modify(c10_value, DECIMAL(9, 3));
alter table paper_score modify(c11_value, DECIMAL(9, 3));
alter table paper_score modify(c12_value, DECIMAL(9, 3));
alter table paper_score modify(c13_value, DECIMAL(9, 3));
alter table paper_score modify(total, DECIMAL(9, 3));

alter table intern_score modify(c14_value, DECIMAL(9, 3));
alter table intern_score modify(c15_value, DECIMAL(9, 3));
alter table intern_score modify(total, DECIMAL(9, 3));

alter table assessment modify(course_quality, DECIMAL(14, 3));
alter table assessment modify(paper_quality, DECIMAL(14, 3));
alter table assessment modify(intern_quality, DECIMAL(14, 3));
alter table assessment modify(other_quality, DECIMAL(14, 3));
alter table assessment modify(total_quality, DECIMAL(14, 3));
