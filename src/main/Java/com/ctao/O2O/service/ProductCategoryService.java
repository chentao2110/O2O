package com.ctao.O2O.service;

import com.ctao.O2O.Exceptions.ProductCategoryOperationException;
import com.ctao.O2O.dto.ProductCategoryExecution;
import com.ctao.O2O.entity.ProductCategory;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProductCategoryService {
    /**
     * 通过shopId获取商品分类列表
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryByShopId(Long shopId );

    ProductCategoryExecution batchInsertProductCategory (List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;

    /**
     * 将此类别下的商品里的id置为null,然后在删除该商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId) throws ProductCategoryOperationException;
}
