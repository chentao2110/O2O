package com.ctao.O2O.dao;

import com.ctao.O2O.BaseTest;
import com.ctao.O2O.entity.HeadLine;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HeadLineDaoTest  extends BaseTest {
    @Autowired
    private HeadLineDao headLineDao;
    @Test
    public void TestAAddHeadLine(){
        HeadLine headLine1 = new HeadLine();
        headLine1.setCreateTime(new Date());
        headLine1.setEnableStatus(1);
        headLine1.setLastEditTime(new Date());
        headLine1.setLineImg("test");
        headLine1.setLineLink("test");
        headLine1.setLineName("测试1");
        headLine1.setPriority(3);
        HeadLine headLine2 = new HeadLine();
        headLine2.setCreateTime(new Date());
        headLine2.setEnableStatus(1);
        headLine2.setLastEditTime(new Date());
        headLine2.setLineImg("test");
        headLine2.setLineLink("test");
        headLine2.setLineName("测试2");
        headLine2.setPriority(3);
        int effectNum = headLineDao.addHeadLine(headLine1);
        assertEquals(1,effectNum);
         effectNum = headLineDao.addHeadLine(headLine2);
        assertEquals(1,effectNum);
    }
    @Test
    public void TestBQueryHeadList(){
        HeadLine headLine2 = new HeadLine();

        headLine2.setEnableStatus(1);
        assertEquals(2,headLineDao.queryHeadLineList(headLine2).size());

    }
    @Test
    public void TestCModifyHeadList(){
        HeadLine headLine2 = new HeadLine();
        headLine2.setLineId(9l);
        headLine2.setCreateTime(new Date());
        headLine2.setEnableStatus(1);
        headLine2.setLastEditTime(new Date());
        headLine2.setLineImg("modifytest");
        headLine2.setLineLink("modifytest");
        headLine2.setLineName("测试2");
        headLine2.setPriority(3);
        assertEquals(1,headLineDao.modifyHeadLine(headLine2));

    }
    @Test
    public  void TestDQueryHeadLineBylineId(){

         assertEquals("测试2",headLineDao.getHeadLineByLineId(9).getLineName());
    }
}
