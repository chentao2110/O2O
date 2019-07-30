package com.ctao.O2O.web.frontend;

import com.ctao.O2O.dao.HeadLineDao;

import com.ctao.O2O.dto.HeadLineExecution;
import com.ctao.O2O.entity.HeadLine;
import com.ctao.O2O.entity.ShopCategory;
import com.ctao.O2O.service.HeadLineService;

import com.ctao.O2O.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/frontend")
public class MainPageController {
    @Autowired
    private HeadLineService headLineService;
    @Autowired
    private ShopCategoryService shopCategoryService;

    @RequestMapping(value = "/listmainpageinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listMainPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        HeadLine headLine = new HeadLine();
        headLine.setEnableStatus(1);

        //先获取店铺一级分类列表
        try {
            shopCategoryList = shopCategoryService.getCategoryList(null);

            //再获取头条可用状态为1的头条
            HeadLineExecution headLineExecution = headLineService.queryHeadLineList(headLine);
            //最后把他们放进modelMap中
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("headLineList", headLineExecution.getHeadLineList());
            modelMap.put("success",true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errmsg", e.getMessage());
        }
        return modelMap;
    }
}
