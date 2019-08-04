package com.ctao.O2O.dto;

import com.ctao.O2O.entity.PersonInfo;
import com.ctao.O2O.enums.PersonStateEnum;

import java.util.List;

public class PersonExecution {
    private int state;
    private String stateInfo;
    private int count ;
    private PersonInfo personInfo;
    private List<PersonInfo> personInfoList;

    public List<PersonInfo> getPersonInfoList() {
        return personInfoList;
    }

    public void setPersonInfoList(List<PersonInfo> personInfoList) {
        this.personInfoList = personInfoList;
    }

    public PersonExecution() {
    }

    public PersonExecution(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public PersonExecution(PersonStateEnum personStateEnum, PersonInfo personInfo) {
        this.state = state;
        this.personInfo = personInfo;
    }

    public PersonExecution(PersonStateEnum personStateEnum, List<PersonInfo> personInfoList) {
        this.state = state;
        this.personInfoList = personInfoList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }
}
