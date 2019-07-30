package com.ctao.O2O.web.frontend;

import com.ctao.O2O.dto.ShopExecution;
import com.ctao.O2O.entity.Area;
import com.ctao.O2O.entity.Shop;
import com.ctao.O2O.entity.ShopCategory;
import com.ctao.O2O.service.AreaService;
import com.ctao.O2O.service.ShopCategoryService;
import com.ctao.O2O.service.ShopService;
import com.ctao.O2O.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.nio.cs.ext.SJIS;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/frontend")
public class shopListController {
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;

    /**
     * 返回商品列表中的shopCategory列表，和区域信息列表
     *
     * @param request request
     * @return 结果
     */
    @RequestMapping(value = "/listshoppageinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listShoppageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        List<Area> areaList = new ArrayList<>();
        ShopCategory shopCategory = new ShopCategory();
        try {
            if (parentId != -1) {

                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategory.setParent(parent);

                shopCategoryList = shopCategoryService.getCategoryList(shopCategory);
            } else {
                shopCategoryList = shopCategoryService.getCategoryList(null);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "店铺列表查询失败");
        }

        try {
            areaList = areaService.getAreaList();
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "区域列表查询失败");
        }
        modelMap.put("shopCategoryList", shopCategoryList);
        modelMap.put("areaList", areaList);
        modelMap.put("success", true);
        return modelMap;
    }

    /**
     * 获取根据条件获取店铺列表
     * @param request request
     * @return 结果
     */
    @RequestMapping(value = "/getshops", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShops(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");

        if (pageIndex >= 1 && pageSize >= 1) {
            int areaId = HttpServletRequestUtil.getInt(request, "areaId");
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
            String shopName = HttpServletRequestUtil.getString(request, "shopName");
            Shop shopCondition = conditionStitching(areaId, parentId, shopCategoryId, shopName);
            ShopExecution pe = shopService.getShopList(shopCondition, pageIndex, pageSize);
            modelMap.put("success", true);
            modelMap.put("shopList", pe.getShopList());
            modelMap.put("count",pe.getCount());

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "页面设置错误");
        }
        return modelMap;
    }

    /**
     *  组合出shop查询条件
     * @param areaId 区域列表
     * @param parentId 店铺分类的父节点
     * @param shopCategoryId 店铺分类
     * @param shopName 店铺名称
     * @return shop
     */
    private Shop conditionStitching(int areaId, long parentId, long shopCategoryId, String shopName) {
        Shop shopCondition = new Shop();
        if (parentId!=-1L){
            ShopCategory parentCategory = new ShopCategory();
            parentCategory.setShopCategoryId(parentId);

            ShopCategory childCategory = new ShopCategory();
            childCategory.setParent(parentCategory);
            shopCondition.setShopCategory(childCategory);
        }
        if (shopCategoryId != -1L){
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        if (areaId!=-1){
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }
        if (shopName!=null){
            shopCondition.setShopName(shopName);
        }
        shopCondition.setEnableStatus(1);
        return shopCondition;
    }
}
