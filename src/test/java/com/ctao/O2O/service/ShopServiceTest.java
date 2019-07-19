package com.ctao.O2O.service;

import com.ctao.O2O.BaseTest;
import com.ctao.O2O.Exceptions.ShopOperationException;
import com.ctao.O2O.dto.ImageHoder;
import com.ctao.O2O.dto.ShopExecution;
import com.ctao.O2O.entity.Area;
import com.ctao.O2O.entity.PersonInfo;
import com.ctao.O2O.entity.Shop;
import com.ctao.O2O.entity.ShopCategory;
import com.ctao.O2O.enums.ShopStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest  {
    @Autowired
    private ShopService shopService;
    @Test
    public void testAddShop()throws ShopOperationException,FileNotFoundException {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店4");
        shop.setShopDesc("test3");
        shop.setShopAddr("test3" );
        shop.setPhone("test3");
        shop.setShopImg("test3");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg = new File("C:/Users/24310/Desktop/xiaohuangren.jpg");
        InputStream is = new FileInputStream(shopImg);

        ShopExecution se = shopService.addShop(shop, new ImageHoder(shopImg.getName(),is));
        assertEquals(ShopStateEnum.CHECK.getState(),se.getState());
    }

}
