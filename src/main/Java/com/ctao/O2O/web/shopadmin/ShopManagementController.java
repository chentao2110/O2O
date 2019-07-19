package com.ctao.O2O.web.shopadmin;

import com.ctao.O2O.Exceptions.ShopOperationException;
import com.ctao.O2O.dto.ImageHoder;
import com.ctao.O2O.dto.ShopExecution;
import com.ctao.O2O.entity.Area;
import com.ctao.O2O.entity.PersonInfo;
import com.ctao.O2O.entity.Shop;
import com.ctao.O2O.entity.ShopCategory;
import com.ctao.O2O.enums.ShopStateEnum;
import com.ctao.O2O.service.AreaService;
import com.ctao.O2O.service.ShopCategoryService;
import com.ctao.O2O.service.ShopService;
import com.ctao.O2O.util.CodeUtil;
import com.ctao.O2O.util.HttpServletRequestUtil;
import com.ctao.O2O.util.ImageUtil;
import com.ctao.O2O.util.PathUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.omg.CORBA_2_3.portable.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String , Object> getShopManagementInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId <= 0){
            Object currentShopObject = request.getSession().getAttribute("currentShop");
            if (currentShopObject == null){
                modelMap.put("redirect", true);
                modelMap.put("url", "/O2O/shopadmin/shoplist");

            }else {
                Shop currentShop = (Shop) currentShopObject;
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());

            }
        }else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
             request.getSession().setAttribute("currentShop", currentShop);
            modelMap.put("redirect", false);
        }
        return modelMap;
    }
    @RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
    @ResponseBody
    private  Map<String , Object> getShopList(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        PersonInfo user = new PersonInfo();
        user.setUserId(1L);
        request.getSession().setAttribute("user",user);
        user = (PersonInfo) request.getSession().getAttribute("user");

        try{
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopExecution se = shopService.getShopList(shopCondition,0,100);
            modelMap.put("shopList",se.getShopList());
            modelMap.put("user",user);
            modelMap.put("success",true);

        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
          return modelMap;
    }
    @RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        List<Area> areaList = new ArrayList<Area>();
        try {
            shopCategoryList = shopCategoryService.getCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }
    @RequestMapping(path = "/getbyshopid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getByShopId(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId>=0){
            try{
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areas = areaService.getAreaList();
                modelMap.put("shop",shop);
                modelMap.put("areaList", areas);
                modelMap.put("success", true);
            }catch (Exception e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }

        return modelMap;
    }
    @RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        //接收并转化相应的参数，包括店铺信息以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);

        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;

            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");

        }
        //修改店铺信息
        if (shop != null && shop.getShopId()!=null) {

            ShopExecution se = new ShopExecution();
            try {
                if (shopImg==null){
                    se = shopService.modifyShop(shop, null);

                }else {
                    se = shopService.modifyShop(shop, new ImageHoder( shopImg.getOriginalFilename(),shopImg.getInputStream()));
                }
                if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);

                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());

                }
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }

            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺Id");
            return modelMap;
        }
    }

    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        //接收并转化相应的参数，包括店铺信息以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);

        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;

            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }
        //店铺注册
        if (shop != null && shopImg != null) {
            PersonInfo owner =  (PersonInfo)request.getSession().getAttribute("user");

            shop.setOwner(owner);
            ShopExecution se = new ShopExecution();
            try {
                se = shopService.addShop(shop, new ImageHoder( shopImg.getOriginalFilename(),shopImg.getInputStream()));
                if (se.getState() == ShopStateEnum.CHECK.getState()) {
                    modelMap.put("success", true);
                    @SuppressWarnings("unchecked")
                   List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                    if (shopList==null||shopList.size()==0){
                        shopList = new ArrayList<>();
                    }
                    shopList.add(se.getShop());
                    request.getSession().setAttribute("shopList",shopList);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());

                }
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }

            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺信息");
            return modelMap;
        }
    }
//    private static void inputStreamToFile(InputStream inputStream, File file){
//        FileOutputStream outputStream = null;
//        try{
//            outputStream = new FileOutputStream(file);
//            int bytesRead = 0;
//            byte[] buffer = new byte[1024];
//            while((bytesRead = inputStream.read(buffer))!=-1){
//                outputStream.write(buffer,0,bytesRead);
//            }
//        }catch (Exception e){
//            throw  new RuntimeException("调用InputStreamToFile产生异常"+e.getMessage());
//        }finally {
//            try {
//            if (outputStream!=null){
//
//                outputStream.close();
//
//            }
//            if (inputStream!=null){
//                inputStream.close();
//            }
//            } catch (IOException e) {
//                throw  new RuntimeException("调用InputStreamToFile关闭IO产生异常"+e.getMessage());
//            }
//        }
//
//    }
}
