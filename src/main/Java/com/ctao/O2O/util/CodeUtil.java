package com.ctao.O2O.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request) {
        String verifyCodeExcepted = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String veriftCodeActual = HttpServletRequestUtil.getString(request, "verifyCode");
        if (veriftCodeActual == null||!verifyCodeExcepted.equals(veriftCodeActual)){
            return false;
        }
        return true ;
    }
}
