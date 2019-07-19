package com.ctao.O2O.dao;

import com.ctao.O2O.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {
    /**
     * 分页查询
     * @param shopCondition
     * @param rowIndex 从第几行开始取
     * @param pageSize 返回的条数
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition , @Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);

    /**
     * 返回queryList总数
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);
    /**
     * 店铺通过店铺id查询查询
     * @param shopId
     * @return
     */
    Shop queryByShopId(Long shopId);
    /**
     * 新增店铺
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺
     * @param shop
     * @return
     */
    int updateShop(Shop shop);
}
