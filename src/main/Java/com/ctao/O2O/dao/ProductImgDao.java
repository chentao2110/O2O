package com.ctao.O2O.dao;

import com.ctao.O2O.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {
    List<ProductImg> queryProductImgList (long productId);

    /**
     * 批量添加商品图片
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);
    int deleteProductImgByProductId(long productId);
}
