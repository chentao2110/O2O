package com.ctao.O2O.service.impl;

import com.ctao.O2O.Exceptions.ShopOperationException;
import com.ctao.O2O.dao.ShopDao;
import com.ctao.O2O.dto.ImageHoder;
import com.ctao.O2O.dto.ShopExecution;
import com.ctao.O2O.entity.Shop;
import com.ctao.O2O.enums.ShopStateEnum;
import com.ctao.O2O.service.ShopService;
import com.ctao.O2O.util.ImageUtil;
import com.ctao.O2O.util.PageCalculator;
import com.ctao.O2O.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.rowIndexCalculator(pageIndex,pageSize);
        List shopList = shopDao.queryShopList(shopCondition,rowIndex,pageSize);
        int count  = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if (shopList != null){
            se.setShopList(shopList);
            se.setCount(count);
        }else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }

        return se;
    }

    @Override
    public Shop getByShopId(Long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    @Transactional
    public ShopExecution modifyShop(Shop shop, ImageHoder thumbnail) throws ShopOperationException {
        if (shop == null||shop.getShopId()==null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else{
            //判断是否需要处理图片
            try{
                if (thumbnail.getImage() != null&&"".equals(thumbnail.getImageName())&&thumbnail.getImageName() != null){
                    Shop shopTemp = shopDao.queryByShopId(shop.getShopId());
                    if (shopTemp.getShopImg()!=null){
                        ImageUtil.deleteFileOrPath(shopTemp.getShopImg());
                    }
                    addShopImg(shop,thumbnail);
                }
                //更新店铺信息
                shop.setLastEditTime(new Date());
              int effectNum = shopDao.updateShop(shop);
                if (effectNum <= 0){
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                }else {
                    return new ShopExecution(ShopStateEnum.SUCCESS);
                }
            }catch (Exception e){
                throw new ShopOperationException("modifyShop error" + e.getMessage());
            }
        }

    }

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, ImageHoder thumbnail) {
        if (shop == null){
           return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }try{
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            int effectNum = shopDao.insertShop(shop);
            if (effectNum<=0){
                throw new ShopOperationException("店铺创建失败");
            }else {
                if (thumbnail.getImage() != null){
                    try{
                        addShopImg(shop,thumbnail);
                    }catch (Exception e){
                        throw  new ShopOperationException("addShopImg error : "+e.getMessage());
                    }
                    effectNum = shopDao.updateShop(shop);
                    if (effectNum<=0){
                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }
        }catch (Exception e){
            throw  new ShopOperationException("addShop error : "+e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    private void addShopImg(Shop shop, ImageHoder thumbnail) {
        //获取存储图片的路径
        String dest = PathUtil.getShopImgPath(shop.getShopId());
        //获取店铺图片的存储路径
        String shopImgAddr = ImageUtil.generateThumbail(thumbnail,dest);
        shop.setShopAddr(shopImgAddr);
    }
}
