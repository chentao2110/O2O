package com.ctao.O2O.web.local;

import com.ctao.O2O.dto.LocalAuthExecution;
import com.ctao.O2O.entity.LocalAuth;
import com.ctao.O2O.entity.PersonInfo;
import com.ctao.O2O.enums.LocalAuthStateEnum;
import com.ctao.O2O.service.LocalAuthService;
import com.ctao.O2O.service.PersonInfoService;
import com.ctao.O2O.util.CodeUtil;
import com.ctao.O2O.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/local")
public class LocalAuthManagementController {
    @Autowired
    private PersonInfoService personInfoService;
    @Autowired
    private LocalAuthService localAuthService;

    /**
     * 注册
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/registerAuth", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> registerLocalAuth(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误");
            return modelMap;
        }
        PersonInfo personInfo = new PersonInfo();
        String localAuthStr = HttpServletRequestUtil.getString(request, "localAuthStr");
        String personInfoStr = HttpServletRequestUtil.getString(request, "personInfoStr");
        try {
            personInfo = mapper.readValue(personInfoStr, PersonInfo.class);
            personInfo.setName("用户" + Math.random() * 1000000);
            LocalAuth localAuth = mapper.readValue(localAuthStr, LocalAuth.class);
            localAuth.setPersonInfo(personInfo);
            LocalAuthExecution lE = localAuthService.addLocalAuth(localAuth);
            if (lE.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
            }
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "获取数据失败");
        }
        return modelMap;
    }

    /**
     * 登录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/userlogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> LoginLocalAuth(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
        //
        ObjectMapper mapper = new ObjectMapper();

            if (needVerify&&!CodeUtil.checkVerifyCode(request)) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "验证码输入错误");
                return modelMap;
            }

        String userName = HttpServletRequestUtil.getString(request, "userName");
        String password = HttpServletRequestUtil.getString(request, "password");
        Boolean checkswitch = HttpServletRequestUtil.getBoolean(request, "checkswitch");

        LocalAuthExecution IE = new LocalAuthExecution();
        try {

            IE = localAuthService.getLocalAuthByName(userName);
            if (IE.getLocalAuth().getPassword().equals(password)) {
                modelMap.put("success", true);
                request.getSession().setAttribute("username", IE.getLocalAuth().getUsername());
                modelMap.put("usertype",checkswitch);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "密码错误");
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "参数获取失败");
        }
        return modelMap;
    }
    @RequestMapping(value = "/getusetype" ,method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getuseType(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String username = HttpServletRequestUtil.getString(request, "username");
        int useType = localAuthService.getuseTypeByUsename(username);
        if (useType<1){
            modelMap.put("success", false);
            modelMap.put("type",1);
        }else {
            modelMap.put("success", true);
            modelMap.put("type",useType);
        }
        return modelMap;
    }
    @RequestMapping(value = "/getsession", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getSessionforuser(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        String username = (String) request.getSession().getAttribute("username");
        if (username!= null){
            modelMap.put("success",true);
            modelMap.put("username", username);
        }else {
            modelMap.put("success", false);
        }
       return modelMap;
    }
}
