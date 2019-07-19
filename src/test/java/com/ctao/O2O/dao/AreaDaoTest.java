package com.ctao.O2O.dao;

import com.ctao.O2O.BaseTest;
import com.ctao.O2O.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AreaDaoTest extends BaseTest {
    @Autowired
    private AreaDao areaDao;
    @Test
    public void testQueryArea(){
        List<Area> list = areaDao.queryArea();
        assertEquals(2,list.size());
    }
}
