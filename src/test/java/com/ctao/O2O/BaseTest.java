package com.ctao.O2O;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:Spring/spring-dao.xml","classpath:Spring/spring-service.xml"})
public class BaseTest {
}
