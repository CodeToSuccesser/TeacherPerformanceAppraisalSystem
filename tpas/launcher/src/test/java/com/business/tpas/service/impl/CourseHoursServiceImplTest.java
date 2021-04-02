package com.business.tpas.service.impl;

import com.business.tpas.base.BaseTest;
import com.business.tpas.entity.CourseHours;
import com.business.tpas.model.CourseHoursModel;
import com.business.tpas.model.CourseHoursSearchModel;
import com.business.tpas.service.CourseHoursService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 课时信息测试类
 **/

class CourseHoursServiceImplTest extends BaseTest {

    @Autowired
    private CourseHoursService courseHoursService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    void testSelectByCourseIdSuccess() {
        List<CourseHours> courseHours = courseHoursService.selectByCourseId(12L);
        Assert.assertEquals(1, courseHours.size());
        Assert.assertEquals(new Long(1), courseHours.get(0).getTeacherId());
        Assert.assertEquals("2019-2020", courseHours.get(0).getSchoolYear());
        Assert.assertEquals(new Integer(0), courseHours.get(0).getSemester());
    }

    @Test
    void insertBatchCourseHoursSuccess() {
        List<CourseHours> courseHoursList = new ArrayList<>();

        CourseHours courseHours = new CourseHours();
        courseHours.setExpNumber(44);
        courseHours.setExperimentHours(44);
        courseHours.setComputerHours(44);
        courseHours.setTeachingHours(44);
        courseHours.setClassed("软件2班");
        courseHours.setSelectedStudent(44);
        courseHours.setCourseId(9L);
        courseHours.setAdminId(1L);
        courseHours.setExpPerNumber(44);
        courseHours.setSchoolYear("2018-2019");
        courseHours.setSemester(0);
        courseHours.setTeacherId(1L);
        courseHours.setSelectionNumber("21H97320-3987");
        courseHoursList.add(courseHours);
        courseHoursList.add(courseHours);

        courseHoursService.insertBatchCourseHours(courseHoursList);

        List<CourseHours> result = courseHoursService.selectByCourseId(9L);
//        Assert.assertEquals(8, result.size());
//        Assert.assertEquals(new Long(1), result.get(0).getTeacherId());
//        Assert.assertEquals("2018-2019", result.get(0).getSchoolYear());
//        Assert.assertEquals(new Integer(0), result.get(0).getSemester());
    }

    @Test()
    void insertBatchCourseHoursFail() {
        List<CourseHours> courseHoursList = new ArrayList<>();

        CourseHours courseHours = new CourseHours();
        courseHours.setExpNumber(44);
        courseHours.setExperimentHours(44);
        courseHours.setComputerHours(44);
        courseHours.setTeachingHours(44);
        courseHours.setClassed("软件2班");
        courseHours.setSelectedStudent(44);
        courseHours.setCourseId(9L);
        courseHours.setAdminId(1L);
        courseHours.setExpPerNumber(44);
        courseHours.setSchoolYear("2018-2019");
        courseHours.setSemester(0);
        courseHours.setTeacherId(20L);
        courseHoursList.add(courseHours);

        try{
            courseHoursService.insertBatchCourseHours(courseHoursList);
            Assert.fail();
        }catch (DataIntegrityViolationException e){
        }
    }

    @Test
    void getCourseHours() {
        CourseHoursSearchModel courseHoursSearchModel = new CourseHoursSearchModel();
        courseHoursSearchModel.setCourseName("数据库系统课程项目");
        List<CourseHoursModel> courseHoursModels = courseHoursService.getCourseHours(courseHoursSearchModel);
        Assert.assertEquals(1, courseHoursModels.size());
        Assert.assertEquals("数据库系统课程项目", courseHoursModels.get(0).getCourseName());
    }

    @Test
    void modifyCourseHours() {
        CourseHoursModel newCourseHoursModel = new CourseHoursModel();
        newCourseHoursModel.setId(4L);
        newCourseHoursModel.setSchoolYear("2021-2022");
        newCourseHoursModel.setCourseCode("21H98640-2741");

//        courseHoursService.modifyCourseHours(newCourseHoursModel);
//        List<CourseHours> result = courseHoursService.selectByCourseId(13L);
//        Assert.assertEquals(1, result.size());
//        Assert.assertEquals("2021-2022", result.get(0).getSchoolYear());

    }

    @Test
    void deleteCourseHours() {
        List<Long> ids = new ArrayList<>();
        ids.add(14L);
        courseHoursService.deleteCourseHours(ids);
        List<CourseHours> result = courseHoursService.selectByCourseId(14L);
        Assert.assertEquals(1, result.size());
    }

    @Test
    void insertCourseHours() {
        CourseHoursModel courseHoursModel = new CourseHoursModel();
        courseHoursModel.setExpNumber(44);
        courseHoursModel.setExperimentHours(44);
        courseHoursModel.setComputerHours(44);
        courseHoursModel.setTeachingHours(44);
        courseHoursModel.setClassed("软件4班");
        courseHoursModel.setSelectedStudent(44);
        courseHoursModel.setCourseId(15L);
        courseHoursModel.setAdminId(1L);
        courseHoursModel.setExpPerNumber(44);
        courseHoursModel.setSchoolYear("2018-2019");
        courseHoursModel.setSemester(0);
        courseHoursModel.setTeacherId(4L);
        courseHoursModel.setCourseCode("21H81240-1297");
        courseHoursModel.setTeacherCode("2020080201");
//        courseHoursService.insertCourseHours(courseHoursModel);
//        List<CourseHours> result = courseHoursService.selectByCourseId(15L);
//
//        Assert.assertEquals(1, result.size());
//        Assert.assertEquals(new Long(15), result.get(0).getCourseId());
    }
}