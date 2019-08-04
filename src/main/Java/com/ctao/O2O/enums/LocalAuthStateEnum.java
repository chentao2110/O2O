package com.ctao.O2O.enums;

public enum LocalAuthStateEnum {
    SUCCESS(1,"操作成功"),
    FAIL(-1,"操作失败"),
    INNER_ERROR(-1001,"内部系统错误"),
    ;

    private int state;
    private String stateInfo;
    private LocalAuthStateEnum(){

    }
    private LocalAuthStateEnum(int state , String stateInfo){
        this.state =state ;
        this.stateInfo = stateInfo;
    }
    public void setState(int state) {
        this.state = state;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
