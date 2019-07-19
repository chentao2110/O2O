package com.ctao.O2O.web.shopadmin;

import com.ctao.O2O.dao.ProductCategoryDao;
import com.ctao.O2O.dto.ProductCategoryExecution;
import com.ctao.O2O.dto.Result;
import com.ctao.O2O.entity.ProductCategory;
import com.ctao.O2O.entity.Shop;
import com.ctao.O2O.enums.ProductCategoryStateEnum;
import com.ctao.O2O.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;
    @RequestMapping(value = "/removeproductcategory" ,method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> removeProductCategory(Long productCategoryId,HttpServletRequest request){
        Map<String , Object> modelMap = new HashMap<>();
        if (productCategoryId != null && productCategoryId > 0){
            try{
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                ProductCategoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId,currentShop.getShopId());
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",pe.getStateInfo());
                }
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请至少选择一个商品类别");
        }
        return modelMap;
    }
    @RequestMapping(value = "/addproductcategorys",method = RequestMethod.POST)
    @ResponseBody
    private Map<String ,Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList, HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        for (ProductCategory pc:
             productCategoryList) {
            pc.setShopId(currentShop.getShopId());
        }
        if (productCategoryList != null && productCategoryList.size()>0){
            try {
                ProductCategoryExecution pe = productCategoryService.batchInsertProductCategory(productCategoryList);
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }
        }
       return  modelMap;
    }
    @RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
    @ResponseBody
    private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request){

        List<ProductCategory> list =null;
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (currentShop != null &&currentShop.getShopId() > 0 ){
            list = productCategoryService.getProductCategoryByShopId(currentShop.getShopId());
            return  new Result<>(true,list);
        }else {
            ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
            return new Result<>(false,ps.getStateInfo(),ps.getState());
        }

    }



}
