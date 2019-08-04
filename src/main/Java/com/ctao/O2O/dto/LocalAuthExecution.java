package com.ctao.O2O.dto;

import com.ctao.O2O.entity.LocalAuth;

import java.util.List;

public class LocalAuthExecution {
    private int state;
    private String stateInfo;
    private LocalAuth localAuth;
    private List<LocalAuth> localAuthList;
    private int count;

    public LocalAuthExecution(int state, List<LocalAuth> localAuthList) {
        this.state = state;
        this.localAuthList = localAuthList;
    }

    public LocalAuthExecution(int state, LocalAuth localAuth) {
        this.state = state;
        this.localAuth = localAuth;
    }

    public LocalAuthExecution(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public LocalAuthExecution() {
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

    public LocalAuth getLocalAuth() {
        return localAuth;
    }

    public void setLocalAuth(LocalAuth localAuth) {
        this.localAuth = localAuth;
    }

    public List<LocalAuth> getLocalAuthList() {
        return localAuthList;
    }

    public void setLocalAuthList(List<LocalAuth> localAuthList) {
        this.localAuthList = localAuthList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
