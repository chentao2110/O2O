package com.ctao.O2O.dao;

import com.ctao.O2O.BaseTest;
import com.ctao.O2O.entity.PersonInfo;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonInfoTest extends BaseTest {
    @Autowired
    private PersonInfoDao personInfoDao;
    @Test
    @Ignore
    public void TestAAddPersonInfo(){
        PersonInfo personInfo = new PersonInfo();
        personInfo.setCreateTime(new Date());
        personInfo.setEmail("55555@qq.com");
        personInfo.setEnableStatus(1);
        personInfo.setGender("男");
        personInfo.setLastEditTime(new Date());
        personInfo.setName("李四");
        personInfo.setProfileImg("test");
        int effectNum = personInfoDao.addPersonInfo(personInfo);
        assertEquals(1,effectNum);
    }
    @Test
    public void TestBGetPersonInfoByUserId(){
        assertEquals("李四1",personInfoDao.getPersonInfoByUserId(2L).getName());
    }
    @Test
    public void  TestCUpdatePersonInfo(){
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(2L);
        personInfo.setCreateTime(new Date());
        personInfo.setEmail("11111@qq.com");
        personInfo.setEnableStatus(1);
        personInfo.setGender("女");
        personInfo.setLastEditTime(new Date());
        personInfo.setName("李四1");
        personInfo.setProfileImg("test1");
        int i = personInfoDao.updatePersonInfoByUserId(personInfo);
        assertEquals(2,i);

    }
    @Test
    public void TestDGetPersonInfoCount(){

        int count = personInfoDao.getPersonInfoCount(null);
        assertEquals(2,count);

    }
}
