package com.ctao.O2O.service;

import com.ctao.O2O.BaseTest;
import com.ctao.O2O.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaService areaService;
    @Test
    public void testGetAreaList(){
        List<Area> areaList = areaService.getAreaList();
        assertEquals("东区",areaList.get(0).getAreaName());
    }
}
