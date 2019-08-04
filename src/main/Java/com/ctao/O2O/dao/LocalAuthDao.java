package com.ctao.O2O.dao;

import com.ctao.O2O.Exceptions.LocalAuthOperationException;
import com.ctao.O2O.entity.LocalAuth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LocalAuthDao {
    /**
     * 注册用户
     * @param localAuth
     * @return
     * @throws LocalAuthOperationException
     */
    int addLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException;

    /**
     * 查询通过用户的
     * @param name 用户名
     * @return
     * @throws LocalAuthOperationException
     */
    LocalAuth getLocalAuthByName(String name) throws LocalAuthOperationException;

    int updateLocalAuth(@Param("localAuthCondition")LocalAuth localAuth) throws LocalAuthOperationException;

    List<LocalAuth> listAllLocalAuth(@Param("localAuthCondition") LocalAuth localAuthCondition, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize) throws LocalAuthOperationException;

    int getLocalAuthCount(@Param("localAuthCondition") LocalAuth localAuth) throws LocalAuthOperationException;
    int getuseTypeByUsename(String name) throws LocalAuthOperationException;
}
