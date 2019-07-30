package com.ctao.O2O.dto;

import com.ctao.O2O.entity.HeadLine;
import com.ctao.O2O.enums.HeadLineStateEnum;

import java.util.List;

public class HeadLineExecution {
    private int state;
    private String  stateInfo;
    private HeadLine headLine;
    private List <HeadLine> headLineList;

    public HeadLineExecution(HeadLineStateEnum state) {
        this.state = state.getState();
        this.stateInfo = state.getStateInfo();
    }


    public HeadLineExecution() {
    }

    public HeadLineExecution(HeadLineStateEnum state, HeadLine headLine) {
        this.state = state.getState();
        this.stateInfo = state.getStateInfo();
        this.headLine = headLine;
    }

    public HeadLineExecution(HeadLineStateEnum state, List<HeadLine> headLineList) {
        this.state = state.getState();
        this.stateInfo = state.getStateInfo();
        this.headLineList = headLineList;
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

    public HeadLine getHeadLine() {
        return headLine;
    }

    public void setHeadLine(HeadLine headLine) {
        this.headLine = headLine;
    }

    public List<HeadLine> getHeadLineList() {
        return headLineList;
    }

    public void setHeadLineList(List<HeadLine> headLineList) {
        this.headLineList = headLineList;
    }
}
