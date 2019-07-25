package com.ctao.O2O.service.impl;

import com.ctao.O2O.Exceptions.ProductCategoryOperationException;
import com.ctao.O2O.Exceptions.ProductOperationException;
import com.ctao.O2O.dao.ProductCategoryDao;
import com.ctao.O2O.dao.ProductDao;
import com.ctao.O2O.dto.ProductCategoryExecution;
import com.ctao.O2O.entity.ProductCategory;
import com.ctao.O2O.enums.ProductCategoryStateEnum;
import com.ctao.O2O.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    private ProductDao productDao;
    @Override
    public List<ProductCategory> getProductCategoryByShopId(Long shopId) {
        return productCategoryDao.getProductCategoryListByshopId(shopId);
    }

    @Override
    public ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
        if (productCategoryList!= null &&productCategoryList.size()>0){
            try {
                int effectNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if (effectNum <= 0) {
                    throw new ProductCategoryOperationException("店铺类别创建失败");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            }catch (Exception e){
                throw  new ProductCategoryOperationException("batchInsert error :" +e.getMessage() );
            }
        }else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }

    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException{
       try{
           int effectNum = 0;
           try {
               effectNum = productDao.updateProductcategoryToNull(productCategoryId);
               if (effectNum<=0){
                   throw  new ProductCategoryOperationException("商品类别更新失败");
               }
           } catch (Exception e) {
               throw new ProductCategoryOperationException("deleteProductCategory error :"+e.getMessage());
           }

           effectNum = productCategoryDao.deleteProductCategory(productCategoryId,shopId);
           if (effectNum > 0){
               return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
           }else {
               throw  new ProductCategoryOperationException("商品类别删除失败");
           }
       }catch (Exception e){
           throw  new ProductCategoryOperationException("deleteProductCategory error :" +e.getMessage() );
       }

    }


}
