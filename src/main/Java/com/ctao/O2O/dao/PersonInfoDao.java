package com.ctao.O2O.dao;

import com.ctao.O2O.Exceptions.PersonOperationException;
import com.ctao.O2O.entity.PersonInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PersonInfoDao {
    /**
     * 注册用户
     * @param personInfo
     * @return
     * @throws PersonOperationException
     */
    int addPersonInfo(PersonInfo personInfo) throws PersonOperationException;

    /**
     * 查询通过用户的
     * @param userId 用户id
     * @return
     * @throws PersonOperationException
     */
    PersonInfo getPersonInfoByUserId(long userId) throws PersonOperationException;

    int updatePersonInfoByUserId(@Param("personInfoCondition") PersonInfo personInfoCondition) throws PersonOperationException;

    List<PersonInfo> listAllPersonInfo(@Param("personInfoCondition") PersonInfo personInfoCondition,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize) throws PersonOperationException;

    int getPersonInfoCount(PersonInfo personInfo) throws PersonOperationException;
}
