package com.ctao.O2O.web.frontend;

import com.ctao.O2O.dto.ProductExecution;
import com.ctao.O2O.entity.Product;
import com.ctao.O2O.entity.ProductCategory;
import com.ctao.O2O.entity.Shop;
import com.ctao.O2O.service.ProductCategoryService;
import com.ctao.O2O.service.ProductService;
import com.ctao.O2O.service.ShopService;
import com.ctao.O2O.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/frontend", method = RequestMethod.GET)
@Controller
public class ShopDetailController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 获取店铺的信息，包括店铺分类和店铺的详细信息
     * @param request re
     * @return 结果
     */
    @RequestMapping(value = "/listshopdetailpageinfo")
    @ResponseBody
    public Map<String, Object> listShopDetailPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId != -1) {
            try {
                Shop Shop = shopService.getByShopId(shopId);
                modelMap.put("success", true);
                modelMap.put("Shop", Shop);
                List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryByShopId(shopId);
                modelMap.put("productCategoryList", productCategoryList);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        }
        return modelMap;
    }

    /**
     * 获取此店铺下的所有商品
     * @param request re
     * @return 结果
     */
    @RequestMapping(value = "/listproductbyshop")
    @ResponseBody
    public Map<String, Object> listProductByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        int pageNum = HttpServletRequestUtil.getInt(request, "pageNum");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        ProductExecution pe;

        if (shopId != -1 && pageNum >= 1 && pageSize >= 1) {
            try {
                Shop shop = new Shop();
                Product product = new Product();
                shop.setShopId(shopId);
                product.setShop(shop);

                pe = productService.queryProductList(product, pageNum, pageSize);
                modelMap.put("success", true);
                modelMap.put("productList", pe.getProductList());
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "页面设置错误");
        }


        return modelMap;
    }

}
