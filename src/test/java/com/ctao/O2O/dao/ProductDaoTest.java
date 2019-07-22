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
import java.util.List;

import static org.junit.Assert.assertEquals;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testAAddProduct() {
        Shop shop1 = new Shop();
        shop1.setShopId(1L);
        ProductCategory pc1 = new ProductCategory();
        pc1.setProductCategoryId(1L);
        // 初始化三个商品实例并添加进shopId为1的店铺里，
        // 同时商品类别Id也为1
        Product product1 = new Product();
        product1.setProductName("测试1");
        product1.setProductDesc("测试Desc1");
        product1.setImgAddr("test1");
        product1.setPriority(1);
        product1.setEnableStatus(1);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setShop(shop1);
        product1.setProductCategory(pc1);

        Product product2 = new Product();
        product2.setProductName("测试2");
        product2.setProductDesc("测试Desc2");
        product2.setImgAddr("test2");
        product2.setPriority(2);
        product2.setEnableStatus(0);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setShop(shop1);
        product2.setProductCategory(pc1);
        Product product3 = new Product();
        product3.setProductName("test3");
        product3.setProductDesc("测试Desc3");
        product3.setImgAddr("test3");
        product3.setPriority(3);
        product3.setEnableStatus(1);
        product3.setCreateTime(new Date());
        product3.setLastEditTime(new Date());
        product3.setShop(shop1);
        product3.setProductCategory(pc1);
        int effectNUm = productDao.insertProduct(product1);
        productDao.insertProduct(product2);
        productDao.insertProduct(product3);
        assertEquals(1, effectNUm);
    }

    @Test
    public void testBqueryProductByProductId() {
        ProductImg productImg1 = new ProductImg();
        long productId = 38l;
        productImg1.setImgAddr("图片1");
        productImg1.setImgDesc("测试图片1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(productId);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(productId);
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        assertEquals(2,productImgDao.batchInsertProductImg(productImgList));
        assertEquals(2,productDao.queryProductByProductId(productId).getProductImgList().size());
        int effectedNum = productImgDao.deleteProductImgByProductId(productId);
        assertEquals(2, effectedNum);

    }

    @Test
    public void testCqueryProductList() {


        assertEquals(35, productDao.queryProductList(new Product(), 0, 50).size());
    }

    @Test
    public void testDUpdateProduct() {
        Product product = new Product();
        ProductCategory pc = new ProductCategory();
        Shop shop = new Shop();
        shop.setShopId(1L);
        pc.setProductCategoryId(2L);
        product.setProductId(1L);
        product.setShop(shop);
        product.setProductName("第二个产品");
        product.setProductCategory(pc);
        int effectNUm = productDao.udateProduct(product);
        assertEquals(1,effectNUm);
    }
    @Test
    public void testEDeleteProduct(){
       Product product = new Product();
       ProductCategory productCategory = new ProductCategory();
       productCategory.setProductCategoryId(1l);
       product.setProductCategory(productCategory);

      List<Product> list = productDao.queryProductList(product,0,50);
        for (Product p :list) {
            productImgDao.deleteProductImgByProductId(p.getProductId());

            assertEquals(1,productDao.deleteProduct(p));

        }
    }
}
