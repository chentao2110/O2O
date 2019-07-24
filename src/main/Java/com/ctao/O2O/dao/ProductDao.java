package com.ctao.O2O.dao;

import com.ctao.O2O.Exceptions.ProductOperationException;
import com.ctao.O2O.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {
    int udateProduct(Product product) throws ProductOperationException;

    /**
     * 查询对应条件的商品总数
     * @param product product
     * @return 条数
     * @throws ProductOperationException ProductOperationException
     */
    int queryProductCount(@Param("productCondition") Product product) throws ProductOperationException;
    /**
     *
     * @param productCondition product
     * @param rowIndex 起始行数
     * @param pageSize 每页大小
     * @return List
     */
    List<Product> queryProductList(@Param("productCondition") Product productCondition,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);
    /**
     * 通过productId查询product
     * @param productId productId
     * @return product
     */
    Product queryProductByProductId(long productId);
    /**
     * 插入商品
     * @param product product
     * @return 状态
     */
    int insertProduct( Product product)throws ProductOperationException;
    int deleteProduct(Product product) throws ProductOperationException;
}
