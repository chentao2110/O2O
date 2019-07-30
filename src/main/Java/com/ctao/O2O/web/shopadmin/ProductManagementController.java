package com.ctao.O2O.web.shopadmin;

import com.ctao.O2O.Exceptions.ProductOperationException;
import com.ctao.O2O.dto.ImageHoder;
import com.ctao.O2O.dto.ProductExecution;
import com.ctao.O2O.entity.Product;
import com.ctao.O2O.entity.ProductCategory;

import com.ctao.O2O.entity.Shop;
import com.ctao.O2O.enums.ProductStateEnum;
import com.ctao.O2O.service.ProductCategoryService;
import com.ctao.O2O.service.ProductService;
import com.ctao.O2O.util.CodeUtil;
import com.ctao.O2O.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.binding.ObjectExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

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
     * @param request http请求
     * @return model
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
        Product product;
        ImageHoder thumbnail = null;
        List<ImageHoder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try{
            // 若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
            if (multipartResolver.isMultipart(request)){
                MultipartRequest multipartRequest  = (MultipartRequest) request;
                MultipartFile multipartFile = multipartRequest.getFile("thumbnail");

                    thumbnail =handleImage(request, thumbnail, productImgList);


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
    @RequestMapping(value = "/getproductlistbyshop", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductListByShop(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        //接收从前端传的信息
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
        String productName = HttpServletRequestUtil.getString(request,"productName");
        String productDesc = HttpServletRequestUtil.getString(request, "productDesc");
        int enableStatus = HttpServletRequestUtil.getInt(request, "enableStatus");
        //判空，
        if ((pageIndex >-1 && pageSize >-1) && (currentShop != null) && (currentShop.getShopId() != null )){
        //根据相应条件进行分页查询
            Product product = settingProduct(currentShop, productCategoryId, productName, productDesc, enableStatus);
            ProductExecution pe = productService.queryProductList(product, pageIndex, pageSize);
            if (pe.getState()==ProductStateEnum.SUCCESS.getState()){
                modelMap.put("success",true);
                modelMap.put("count",pe.getCount());
                modelMap.put("productList",pe.getProductList());
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errmsg","页码设置错误或获取不到店铺信息");
        }

        //返回结果
        return modelMap;
    }

    private Product settingProduct(Shop currentShop, long productCategoryId,
                                   String productName, String productDesc, int enableStatus) {
        Product product = new Product();
        ProductCategory productCategory = new ProductCategory();

        if (productCategoryId != -1l) {
            productCategory.setProductCategoryId(productCategoryId);
            product.setProductCategory(productCategory);
        }
        if (productName != null) {
            product.setProductName(productName);
        }
        if (productDesc != null) {
            product.setProductDesc(productDesc);
        }
        if (enableStatus==1||enableStatus ==0) {
            product.setEnableStatus(enableStatus);
        }


            product.setShop(currentShop);

        return product;
    }

    @RequestMapping(value = "/modifyproduct",method = RequestMethod.POST)
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
               thumbnail = handleImage(request,thumbnail,productImgs);
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
     * @param productId 商品id
     * @return modelMap
     */
    @RequestMapping(value = "/getproductbyid",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getProductById(@RequestParam Long productId,HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        if (productId != null ){
            ProductExecution pe = productService.queryProductByProductId(productId);
            Shop currentShop  = (Shop) request.getSession().getAttribute("currentShop");
            List<ProductCategory> productCategoryList;
            if (currentShop!=null && currentShop.getShopId()!=null){

                productCategoryList = productCategoryService.getProductCategoryByShopId(currentShop.getShopId());
                modelMap.put("product",pe.getProduct());

                modelMap.put("success",true);

                modelMap.put("productCategoryList",productCategoryList);
            }else {
                modelMap.put("success",false);
                modelMap.put("errmsg","店铺id不能为空");
            }


        }else {
            modelMap.put("success",false);
            modelMap.put("errmsg","商品id不能为空");
        }
        return modelMap;
    }

    /**
     * 图片处理
     * @param request request
     * @param thumbnail 略缩图
     * @param productImgList 详情图列表
     * @return modelMap
     * @throws IOException io
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
