package com.ctao.O2O.web.frontend;

import com.ctao.O2O.dto.ProductExecution;
import com.ctao.O2O.service.ProductService;
import com.ctao.O2O.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/frontend", method = RequestMethod.GET)
public class ProductDetailController {
    @Autowired
    private ProductService productService;
    @RequestMapping("/listproductdetailinfo")
    @ResponseBody
    public Map<String, Object> listProductDetailInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long productId = HttpServletRequestUtil.getLong(request, "productId");
        if (productId != -1) {
            try {
                ProductExecution pe = productService.queryProductByProductId(productId);
                modelMap.put("success", true);
                modelMap.put("product",pe.getProduct());
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }

        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "商品Id获取失败");
        }
        return modelMap;
    }
}
