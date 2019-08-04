package com.ctao.O2O.service;

import com.ctao.O2O.dto.PersonExecution;
import com.ctao.O2O.entity.PersonInfo;

public interface PersonInfoService {
    PersonExecution getPesonInfoByUserId(long userId) ;
    PersonExecution updatePersonInfoByUserId(PersonInfo personInfo) ;
    PersonExecution getPersonInfoCount(PersonInfo personInfo) ;
    PersonExecution listAllPersonInfo(PersonInfo personInfo, int rowIndex, int pageSize) ;

}
