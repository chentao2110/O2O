package com.ctao.O2O.dto;

import com.ctao.O2O.entity.Product;
import com.ctao.O2O.enums.ProductStateEnum;

import java.util.List;

public class ProductExecution {
    //结果状态
    private int state;
    //状态标识
    private String stateInfo;

    //类别数量
    private int count;

    private Product product;
    //product列表
    private List<Product> productList;

    public ProductExecution(){

    }
    //操作失败时使用的构造器
    public ProductExecution(ProductStateEnum state){
        this.state = state.getState();
        this.stateInfo = state.getStateInfo();
    }
    //操作成功时使用的构造器
    public ProductExecution(ProductStateEnum state,Product product){
        this.product = product;
        this.state = state.getState();
        this.stateInfo = state.getStateInfo();
    }
    //操作成功时使用的构造器
    public ProductExecution(ProductStateEnum state ,List<Product> productList){
        this.state = state.getState();
        this.stateInfo = state.getStateInfo();
        this.productList = productList;
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
