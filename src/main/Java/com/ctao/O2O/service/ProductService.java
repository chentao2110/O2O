package com.ctao.O2O.service;

import com.ctao.O2O.Exceptions.ProductOperationException;
import com.ctao.O2O.dto.ImageHoder;
import com.ctao.O2O.dto.ProductExecution;
import com.ctao.O2O.entity.Product;

import java.io.InputStream;
import java.util.List;

public interface ProductService {
    /**
     * 通过productId查询product
     * @param productId
     * @return
     */
    ProductExecution queryProductByProductId(long productId);

    /**
     * 删除商品
     * @param product
     * @return
     */
    ProductExecution deleteProduct(Product product);

    ProductExecution queryProductList(Product product ,int rowIndex,int pageSize);

    /**
     * 更改商品信息
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductOperationException
     */
    ProductExecution updateProduct(Product product,ImageHoder thumbnail , List<ImageHoder> productImgList) throws ProductOperationException;

    /**
     * 单个插入product
     * @param product
     * @return
     */

    ProductExecution addProduct(Product product, ImageHoder thumbnail, List<ImageHoder> productImgList);
}
