package com.ctao.O2O.dao;

import com.ctao.O2O.BaseTest;
import com.ctao.O2O.entity.Product;
import com.ctao.O2O.entity.ProductCategory;
import com.ctao.O2O.entity.ProductImg;
import com.ctao.O2O.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {
    @Autowired
    private ProductImgDao productImgDao;
    @Test
    public void testAAddProductImg() {
        ArrayList<ProductImg> productImgList = new ArrayList();
        ProductImg productImg1 = new ProductImg();
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(1l);
        productImg1.setImgAddr("图片1");
        productImg1.setImgDesc("测试的图片1");
        productImg1.setPriority(3);
        productImg1.setProductImgId(1l);
        productImgList.add(productImg1);
        ProductImg productImg2 = new ProductImg();
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(1l);
        productImg2.setImgAddr("图片2");
        productImg2.setImgDesc("测试的图片2");
        productImg2.setPriority(3);
        productImg2.setProductImgId(2l);
        productImgList.add(productImg2);

       int effectNum =  productImgDao.batchInsertProductImg(productImgList);
        System.out.println(effectNum);
       assertEquals(2,effectNum);
    }
    @Test
    public void testBqueryProductImgList(){
        assertEquals(2,productImgDao.queryProductImgList(1l).size());
    }
    @Test
    public void testCDeleteProductImgByProductId(){
        assertEquals(2,productImgDao.deleteProductImgByProductId(1l));
    }
}
