package com.ctao.O2O.service.impl;

import com.ctao.O2O.Exceptions.ProductOperationException;
import com.ctao.O2O.dao.ProductDao;
import com.ctao.O2O.dao.ProductImgDao;
import com.ctao.O2O.dto.ImageHoder;
import com.ctao.O2O.dto.ProductExecution;
import com.ctao.O2O.entity.Product;
import com.ctao.O2O.entity.ProductImg;
import com.ctao.O2O.enums.ProductStateEnum;
import com.ctao.O2O.service.ProductImgService;
import com.ctao.O2O.service.ProductService;
import com.ctao.O2O.util.ImageUtil;
import com.ctao.O2O.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductImgDao productImgDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public Product queryProductByProductId(long productId) {
        return productDao.queryProductByProductId(productId);
    }

    @Override
    @Transactional
    public ProductExecution updateProduct(Product product, ImageHoder thumbnail, List<ImageHoder> productImgList) throws ProductOperationException {
        if (product == null || product.getShop() == null || product.getShop().getShopId()==null){
            return new ProductExecution(ProductStateEnum.EMPTY);
        }else {
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            if (thumbnail != null){
                addThumbnail(product,thumbnail);
            }
            try{
                int effectNum = productDao.udateProduct(product);
                if (effectNum <= 0){
                    throw  new ProductOperationException("更改店铺失败");
                }
            }catch (Exception e){
                throw new ProductOperationException("updateProduct error :" +e.getMessage());
            }
            deleteProductImgList(product);
            if (productImgList != null && productImgList.size()>0 ){
                addProductImgList(product,productImgList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS);
        }

    }

    @Override
    @Transactional
    public ProductExecution addProduct(Product product, ImageHoder thumbnail, List<ImageHoder> productImgList) throws ProductOperationException {
        if (product != null && product.getShop()!=null && product.getShop().getShopId() != null){
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            if (thumbnail!=null){
                addThumbnail(product,thumbnail);
            }
            try{
                int effectNum = productDao.insertProduct(product);
                if (effectNum <= 0){
                    throw new ProductOperationException("创建商品失败");
                }
            }catch (Exception e){
                throw new ProductOperationException("addProduct error :"+e.getMessage());
            }
            if (productImgList != null && productImgList.size()>0 ){
                addProductImgList(product,productImgList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS);
        }else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }
    private void deleteProductImgList(Product product){
        try {
            if (product != null && product.getProductId() != null){
                int effectNum = productImgDao.deleteProductImgByProductId(product.getProductId());
                if (effectNum <= 0){
                    throw new ProductOperationException("删除商品详情图失败");
                }
            }else {
                throw new ProductOperationException("商品为空或者商品ID为空");
            }
        }catch (Exception e){
            throw new ProductOperationException("delete productImg error :" +e.getMessage());
        }
    }
    private void addProductImgList(Product product, List<ImageHoder> productImgHolderList) {
        String dest = PathUtil.getShopImgPath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        for (ImageHoder productImgHolder:productImgHolderList) {
            String imgAddr = ImageUtil.generateNormalImg(productImgHolder,dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        if (productImgList.size() > 0){
            try{
                int effectNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectNum<0){
                    throw  new ProductOperationException("创建商品详情图失败");
                }
            }catch (Exception e){
                throw  new ProductOperationException("创建商品详情图失败");
            }
        }

    }

    private void addThumbnail(Product product, ImageHoder thumbnail) {
        String dest = PathUtil.getShopImgPath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbail(thumbnail,dest);
        product.setImgAddr(thumbnailAddr);
    }


}
