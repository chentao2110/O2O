package com.ctao.O2O.enums;

public enum  ProductCategoryStateEnum {

    SUCCESS(1,"操作成功"),
    INNER_ERROR(-1001,"操作失败"),
    EMPTY_LIST(-1002,"list列表为空");


    private int state;
    private String stateInfo;

    private ProductCategoryStateEnum(int state,String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }
    public static ProductCategoryStateEnum stateof(int state){
        for (ProductCategoryStateEnum stateEnum : values()){
            if (stateEnum.getState()==state){
                return  stateEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
