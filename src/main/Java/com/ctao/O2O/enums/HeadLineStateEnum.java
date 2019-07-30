package com.ctao.O2O.enums;

public enum HeadLineStateEnum {
    SUCCESS(1,"操作成功"),
    INNER_ERROR(-1001,"操作失败"),
    EMPTY_LIST(-1002,"list列表为空");

    private int state;
    private String stateInfo;

    private HeadLineStateEnum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }
    public static HeadLineStateEnum stateof(int state){
        for (HeadLineStateEnum stateEnum : values()){
            if (stateEnum.getState()==state){
                return  stateEnum;
            }
        }
        return null;
    }
    public Integer getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
