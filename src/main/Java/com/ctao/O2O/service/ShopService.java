package com.ctao.O2O.service;

import com.ctao.O2O.Exceptions.ShopOperationException;
import com.ctao.O2O.dto.ImageHoder;
import com.ctao.O2O.dto.ShopExecution;
import com.ctao.O2O.entity.Shop;

import java.io.InputStream;
import java.util.List;

public interface ShopService {
    ShopExecution getShopList(Shop shopCondition, int pageIndex , int pageSize);
    /**
     * 通过对shopId获取Shop
     * @param shopId
     * @return
     */
    Shop getByShopId(Long shopId);
    /**
     * 更改店铺的信息，包括对图片的处理
     * @param shop

     * @param thumbnail
     * @return
     */
    ShopExecution modifyShop(Shop shop , ImageHoder thumbnail) throws ShopOperationException;
    /**
     * 注册店铺信息，包括对图片的处理
     * @param shop

     * @param thumbnail
     * @return
     */

    ShopExecution addShop(Shop shop, ImageHoder thumbnail) throws ShopOperationException;
}
