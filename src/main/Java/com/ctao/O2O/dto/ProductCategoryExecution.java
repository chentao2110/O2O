package com.ctao.O2O.dto;

import com.ctao.O2O.entity.ProductCategory;
import com.ctao.O2O.enums.ProductCategoryStateEnum;


import java.util.List;

public class ProductCategoryExecution {
    //结果状态
    private int state;
    //状态标识
    private String stateInfo;

    //类别数量
    private int count;


    //productCategory列表
    private List<ProductCategory> productCategorieList;
    public ProductCategoryExecution(){

    }
    //操作失败时的使用构造器
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //操作成功时的使用构造器
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum ,List<ProductCategory> productCategorieList){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productCategorieList = productCategorieList;
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

    public List<ProductCategory> getProductCategorieList() {
        return productCategorieList;
    }

    public void setProductCategorieList(List<ProductCategory> productCategorieList) {
        this.productCategorieList = productCategorieList;
    }
}
