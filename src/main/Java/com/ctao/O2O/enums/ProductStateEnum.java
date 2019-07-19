package com.ctao.O2O.enums;

public enum ProductStateEnum {
    SUCCESS(1,"操作成功"),
    INNER_ERROR(-1001,"操作失败"),
    EMPTY(-1002,"product或shop或shopId为空");
    private Integer state;
    private String stateInfo;
    private ProductStateEnum(Integer state,String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ProductStateEnum stateof(int state){
        for(ProductStateEnum stateEnum :values()){
            if (stateEnum.getState() ==state){
                return stateEnum;
            }
        }
        return null;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }
}
