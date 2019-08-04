package com.ctao.O2O.service;

import com.ctao.O2O.dto.LocalAuthExecution;
import com.ctao.O2O.entity.LocalAuth;

public interface LocalAuthService {
    /**
     * 注册用户
     * @param localAuth
     * @return
     */
    LocalAuthExecution addLocalAuth(LocalAuth localAuth);

    /**
     * 密码重置
     * @param localAuth
     * @return
     */
    LocalAuthExecution updateLocalAuth(LocalAuth localAuth);

    /**
     * 获取登录信息
     * @param name
     * @return
     */
    LocalAuthExecution getLocalAuthByName(String name);

    /**
     * 获取用户总数
     * @param localAuth
     * @return
     */


    /**
     * 获取用户列表
     * @param localAuthLocalAuth
     * @param rowIndex
     * @param pageSize
     * @return
     */
    LocalAuthExecution listAllLocalAuth(LocalAuth localAuthLocalAuth ,  int rowIndex, int pageSize);

    int getuseTypeByUsename(String name);

}
