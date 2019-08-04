package com.ctao.O2O.service.impl;

import com.ctao.O2O.Exceptions.PersonOperationException;
import com.ctao.O2O.dao.PersonInfoDao;
import com.ctao.O2O.dto.PersonExecution;
import com.ctao.O2O.entity.PersonInfo;
import com.ctao.O2O.enums.PersonStateEnum;
import com.ctao.O2O.service.PersonInfoService;
import com.ctao.O2O.util.PageCalculator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {
    private PersonInfoDao personInfoDao;
    @Override
    public PersonExecution getPesonInfoByUserId(long userId) {
        PersonExecution personExecution = new PersonExecution();

            personExecution.setPersonInfo( personInfoDao.getPersonInfoByUserId(userId));
        return personExecution;
    }

    @Override
    public PersonExecution updatePersonInfoByUserId(PersonInfo personInfo) {
        PersonExecution personExecution = new PersonExecution();
        if (personInfo!=null && personInfo.getUserId()!=null){
            int effectNum = personInfoDao.updatePersonInfoByUserId(personInfo);
            if (effectNum>0){
                personExecution.setState(PersonStateEnum.SUCCESS.getState());
                personExecution.setStateInfo(PersonStateEnum.SUCCESS.getStateInfo());
            }else {
                personExecution.setStateInfo(PersonStateEnum.INNER_ERROR.getStateInfo());
                personExecution.setState(PersonStateEnum.INNER_ERROR.getState());
            }
        }else {
            throw new PersonOperationException("参数为空");
        }
        return personExecution;
    }

    @Override
    public PersonExecution getPersonInfoCount(PersonInfo personInfo) {
        PersonExecution personExecution = new PersonExecution();
        if (personInfo!=null ){
            int effectNum = personInfoDao.getPersonInfoCount(personInfo);
            personExecution.setCount(effectNum);
        }else {
            throw new PersonOperationException("参数为空");
        }
        return personExecution;
    }

    @Override
    public PersonExecution listAllPersonInfo(PersonInfo personInfo, int pageIndex, int pageSize) {
        PersonExecution personExecution = new PersonExecution();
        int rowIndex = PageCalculator.rowIndexCalculator(pageIndex, pageSize);
        List<PersonInfo> personInfoList = new ArrayList<>();
        if (personInfo!=null &&rowIndex>0&&pageSize>0){
            personInfoList = personInfoDao.listAllPersonInfo(personInfo,rowIndex,pageSize);
            if (personInfoList.size()>0){
                personExecution.setCount(getPersonInfoCount(personInfo).getCount());
                personExecution.setPersonInfoList(personInfoList);
                personExecution.setState(PersonStateEnum.SUCCESS.getState());
                personExecution.setStateInfo(PersonStateEnum.SUCCESS.getStateInfo());
            }else {
                personExecution.setState(PersonStateEnum.INNER_ERROR.getState());
                personExecution.setStateInfo(PersonStateEnum.INNER_ERROR.getStateInfo());
            }
        }else {
            throw new PersonOperationException("参数为空");
        }
        return personExecution;
    }
}
