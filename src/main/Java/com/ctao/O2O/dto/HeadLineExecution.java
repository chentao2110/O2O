package com.ctao.O2O.dto;

import com.ctao.O2O.entity.HeadLine;

import java.util.List;

public class HeadLineExecution {
    private int state;
    private String  stateInfo;
    private HeadLine headLine;
    private List <HeadLine> headLineList;

    public HeadLineExecution(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public HeadLineExecution() {
    }

    public HeadLineExecution(int state, String stateInfo, HeadLine headLine) {
        this.state = state;
        this.stateInfo = stateInfo;
        this.headLine = headLine;
    }

    public HeadLineExecution(int state, String stateInfo, List<HeadLine> headLineList) {
        this.state = state;
        this.stateInfo = stateInfo;
        this.headLineList = headLineList;
    }
}
