package com.ctao.O2O.dao;

import com.ctao.O2O.Exceptions.ProductOperationException;
import com.ctao.O2O.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {
    int udateProduct(Product product) throws ProductOperationException;

    List queryProductList(@Param("ProductCondition") Product productCondition,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);
    /**
     * 通过productId查询product
     * @param productId
     * @return
     */
    Product queryProductByProductId(long productId);
    /**
     * 插入商品
     * @param product
     * @return
     */
    int insertProduct( Product product)throws ProductOperationException;
}
