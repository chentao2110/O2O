package com.ctao.O2O.dao;

import com.ctao.O2O.BaseTest;
import com.ctao.O2O.entity.Area;
import com.ctao.O2O.entity.PersonInfo;
import com.ctao.O2O.entity.Shop;
import com.ctao.O2O.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class shopDaoTest extends BaseTest {
    @Autowired ShopDao shopDao;
    @Test
    public void testInsertShop(){
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店");
        shop.setShopDesc("test");
        shop.setShopAddr("test" );
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1,effectedNum);
    }
@Test
    public void testUpdateShop(){
        Shop shop = new Shop();
        shop.setShopId(1L);

        shop.setShopDesc("测试描述");
        shop.setShopAddr("测试地址" );
        shop.setPhone("test");
        shop.setLastEditTime(new Date());
        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1,effectedNum);
    }
}
