package com.business.tpas.base;

import com.launcher.tpas.Main;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @description 单元测试基类，其余测试类需继承该类
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
@ActiveProfiles(value = "test")
@ComponentScan(basePackages = {"com.management", "com.business"})
public class BaseTest {
}
