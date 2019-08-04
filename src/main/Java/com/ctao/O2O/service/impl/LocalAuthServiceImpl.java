package com.ctao.O2O.service.impl;

import com.ctao.O2O.Exceptions.LocalAuthOperationException;
import com.ctao.O2O.Exceptions.PersonOperationException;
import com.ctao.O2O.dao.LocalAuthDao;
import com.ctao.O2O.dao.PersonInfoDao;
import com.ctao.O2O.dto.LocalAuthExecution;
import com.ctao.O2O.entity.LocalAuth;
import com.ctao.O2O.entity.PersonInfo;
import com.ctao.O2O.enums.LocalAuthStateEnum;
import com.ctao.O2O.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocalAuthServiceImpl implements com.ctao.O2O.service.LocalAuthService {
    @Autowired
    private LocalAuthDao localAuthDao;
    @Autowired
    private PersonInfoDao personInfoDao;

    @Transactional
    @Override
    public LocalAuthExecution addLocalAuth(LocalAuth localAuth) {
        LocalAuthExecution localAuthExecution = new LocalAuthExecution();
        if (localAuth != null && localAuth.getUsername() != null && localAuth.getPassword() != null
                && localAuth.getPersonInfo() != null && localAuth.getPersonInfo().getUserId() != null) {
            int effect = 0;
            int effectNum = 0;
            try {
                PersonInfo personInfo = new PersonInfo();
                personInfo.setUserId(localAuth.getPersonInfo().getUserId());
                effect = personInfoDao.addPersonInfo(personInfo);
                effectNum = localAuthDao.addLocalAuth(localAuth);
            } catch (Exception e) {
                throw new LocalAuthOperationException(e.getMessage());
            }
            if (effectNum > 0 && effect > 0) {
                localAuthExecution.setState(LocalAuthStateEnum.SUCCESS.getState());
                localAuthExecution.setStateInfo(LocalAuthStateEnum.SUCCESS.getStateInfo());
            } else {
                localAuthExecution.setState(LocalAuthStateEnum.FAIL.getState());
                localAuthExecution.setStateInfo(LocalAuthStateEnum.FAIL.getStateInfo());
            }
        } else {
            throw new LocalAuthOperationException("参数为空");
        }
        return localAuthExecution;
    }

    @Transactional
    @Override
    public LocalAuthExecution updateLocalAuth(LocalAuth localAuth) {
        LocalAuthExecution localAuthExecution = new LocalAuthExecution();
        if (localAuth != null) {

            int effectNum = localAuthDao.updateLocalAuth(localAuth);
            if (effectNum > 0) {
                localAuthExecution.setState(LocalAuthStateEnum.SUCCESS.getState());
                localAuthExecution.setStateInfo(LocalAuthStateEnum.SUCCESS.getStateInfo());
            } else {
                localAuthExecution.setState(LocalAuthStateEnum.FAIL.getState());
                localAuthExecution.setStateInfo(LocalAuthStateEnum.FAIL.getStateInfo());
            }
        } else {
            throw new LocalAuthOperationException("参数为空");
        }
        return localAuthExecution;
    }

    @Transactional
    @Override
    public LocalAuthExecution getLocalAuthByName(String name) {
        LocalAuthExecution localAuthExecution = new LocalAuthExecution();
        if (name != null) {
            LocalAuth localAuth = new LocalAuth();
            localAuth = localAuthDao.getLocalAuthByName(name);
            if (localAuth != null) {
                localAuthExecution.setLocalAuth(localAuth);
                localAuthExecution.setState(LocalAuthStateEnum.SUCCESS.getState());
                localAuthExecution.setStateInfo(LocalAuthStateEnum.SUCCESS.getStateInfo());
            } else {
                localAuthExecution.setState(LocalAuthStateEnum.FAIL.getState());
                localAuthExecution.setStateInfo(LocalAuthStateEnum.FAIL.getStateInfo());
            }
        } else {
            throw new LocalAuthOperationException("参数为空");
        }
        return localAuthExecution;
    }


    @Transactional
    @Override
    public LocalAuthExecution listAllLocalAuth(LocalAuth localAuthLocalAuth, int PageIndex, int pageSize) {
        LocalAuthExecution localAuthExecution = new LocalAuthExecution();
        int rowIndex = PageCalculator.rowIndexCalculator(PageIndex, pageSize);
        if (localAuthLocalAuth != null && rowIndex >= 0 && pageSize >= 1) {

            List<LocalAuth> localAuthList = new ArrayList<>();
            localAuthList = localAuthDao.listAllLocalAuth(localAuthLocalAuth, rowIndex, pageSize);
            if (localAuthList != null) {
                localAuthExecution.setLocalAuthList(localAuthList);
                localAuthExecution.setState(LocalAuthStateEnum.SUCCESS.getState());
                localAuthExecution.setStateInfo(LocalAuthStateEnum.SUCCESS.getStateInfo());
            } else {
                localAuthExecution.setState(LocalAuthStateEnum.FAIL.getState());
                localAuthExecution.setStateInfo(LocalAuthStateEnum.FAIL.getStateInfo());
            }
        } else {
            throw new LocalAuthOperationException("参数为空或页码设置失败");
        }
        return localAuthExecution;
    }

    @Override
    public int getuseTypeByUsename(String name) {
        int useType = 0;
        if (name !=null){
             useType = localAuthDao.getuseTypeByUsename(name);
        }

        return useType;
    }
}
