package com.ctao.O2O.web.shopadmin;

import com.ctao.O2O.Exceptions.ProductOperationException;
import com.ctao.O2O.dto.ImageHoder;
import com.ctao.O2O.dto.ProductExecution;
import com.ctao.O2O.entity.Product;
import com.ctao.O2O.entity.ProductCategory;
import com.ctao.O2O.entity.ProductImg;
import com.ctao.O2O.entity.Shop;
import com.ctao.O2O.enums.ProductStateEnum;
import com.ctao.O2O.service.ProductCategoryService;
import com.ctao.O2O.service.ProductService;
import com.ctao.O2O.util.CodeUtil;
import com.ctao.O2O.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    private static final int IMAGEMAXCOUNT = 6;

    /**
     * 添加product
     * @param request
     * @return
     */
    @RequestMapping(value = "/addproduct",method = RequestMethod.POST)
    @ResponseBody
    public Map<String ,Object> addProduct(HttpServletRequest request){
        Map<String ,Object> modelMap = new HashMap<>();
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }

        // 接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        ImageHoder thumbnail = null;
        List<ImageHoder> productImgList = new ArrayList<ImageHoder>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try{
            // 若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
            if (multipartResolver.isMultipart(request)){
                thumbnail = handleImage(request,thumbnail,productImgList);
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg","上传的图片不能为空");
                return modelMap;
            }

        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        try {
            String productStr = HttpServletRequestUtil.getString(request,"productStr");
            // 尝试获取前端传过来的表单string流并将其转换成Product实体类
            product = mapper.readValue(productStr,Product.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }

        // 若Product信息，缩略图以及详情图列表为非空，则开始进行商品添加操作
        if (product != null && thumbnail != null && productImgList.size()>0){
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);

                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());

                }
            }catch (ProductOperationException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }

        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入商品信息");
        }
        return modelMap;

    }

    @RequestMapping(value = "/modifyProduct",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> modifyProduct(HttpServletRequest request){
        Map<String,Object> modelMap  = new HashMap<>();
        if (CodeUtil.checkVerifyCode(request)){
           modelMap.put("success",false);
           modelMap.put("errmsg","验证码错误");
        }
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        ImageHoder thumbnail = null;
        List<ImageHoder> productImgs = new ArrayList<>();
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            if (resolver.isMultipart(request)) {
              thumbnail =  handleImage(request,thumbnail,productImgs);
            }

            try {
                product = mapper.readValue(HttpServletRequestUtil.getString(request, "productStr"), Product.class);
            } catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("errmsg",e.toString());
            }
            if (product != null){
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                ProductExecution pe = productService.updateProduct(product, thumbnail, productImgs);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);

                }else {
                    modelMap.put("success",false);
                    modelMap.put("errmsg",pe.getStateInfo());
                }

            }else {
                modelMap.put("success",false);
                modelMap.put("errmsg","商品信息不能为空");
            }

        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errmsg",e.toString());
        }
        return modelMap;
    }
    /**
     * 通过productid获取product和productCategory
     * @param productId
     * @return
     */
    @RequestMapping(value = "/getproductbyid",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getProductById(@RequestParam Long productId){
        Map<String,Object> modelMap = new HashMap<>();
        if (productId != null ){
            Product product = productService.queryProductByProductId(productId);
            List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryByShopId(productId);
            modelMap.put("product",product);
            modelMap.put("productCategoryList",productCategoryList);
        }else {
            modelMap.put("success",false);
            modelMap.put("errmsg","店铺id不能为空");
        }
        return modelMap;
    }

    /**
     * 图片处理
     * @param request
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws IOException
     */
    private ImageHoder handleImage(HttpServletRequest request, ImageHoder thumbnail, List<ImageHoder> productImgList) throws IOException {
        MultipartRequest multipartRequest = (MultipartRequest) request;
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
        if (thumbnailFile != null){
            thumbnail = new ImageHoder(thumbnailFile.getOriginalFilename(),thumbnailFile.getInputStream());
        }
        for (int i = 0; i < IMAGEMAXCOUNT; i++) {
            CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg"+i);
            if (productImgFile != null){
                ImageHoder productImg = new ImageHoder(productImgFile.getOriginalFilename(),productImgFile.getInputStream());
                productImgList.add(productImg);
            }else {
                break;
            }

        }
        return thumbnail;
    }
}
