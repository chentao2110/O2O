package com.ctao.O2O.dto;

public class Result<T> {
    private boolean success;    //是否成功标志
    private T data;             //结果数据
    private String errMsg;      //错误信息
    private  Integer errCode;   //错误代码
    public Result(){

    }
    //成功时的构造器
    public Result(boolean success,T data){
        this.success = success;
        this.data = data ;
    }
    //失败时的构造器
    public  Result (boolean success ,String errMsg , Integer errCode){
        this.success = success ;
        this.errCode = errCode;
        this.errMsg = errMsg ;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }
}
