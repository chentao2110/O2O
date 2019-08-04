package com.ctao.O2O.dao;

import com.ctao.O2O.BaseTest;
import com.ctao.O2O.entity.LocalAuth;
import com.ctao.O2O.entity.PersonInfo;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthTest extends BaseTest {
    @Autowired
    private LocalAuthDao localAuthDao;
    @Test
    @Ignore
    public void TestAAddLocalAuth() {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(1L);
        LocalAuth localAuth = new LocalAuth();
        localAuth.setPersonInfo(personInfo);
        localAuth.setCreateTime(new Date());
        localAuth.setLastEditTime(new Date());
        localAuth.setPassword("123456");
        localAuth.setUsername("abcdf");
        int effectNum = localAuthDao.addLocalAuth(localAuth);
        assertEquals(1, effectNum);
    }
    @Test
    public void TestBGetLocalAuthByName(){
        assertEquals("654321",localAuthDao.getLocalAuthByName("abcdf").getPassword());
    }
    @Test
    @Ignore
    public void TestCUpdateLocalAuth(){
        LocalAuth localAuth = new LocalAuth();
        localAuth.setPassword("654321");
        localAuth.setUsername("abcdf");
        assertEquals(1,localAuthDao.updateLocalAuth(localAuth));
    }
    @Test
    public void TestDListAllLocalAuth(){
        LocalAuth localAuth = new LocalAuth();
        localAuth.setUsername("abcdf");
        assertEquals(1,localAuthDao.listAllLocalAuth(localAuth,1,99).size());
    }
}
