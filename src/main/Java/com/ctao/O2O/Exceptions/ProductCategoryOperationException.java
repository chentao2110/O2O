package com.ctao.O2O.Exceptions;

import java.io.Serializable;

public class ProductCategoryOperationException extends RuntimeException implements Serializable{

    private static final long serialVersionUID = -445122918378841471L;

    public ProductCategoryOperationException(String msg){
        super(msg);
    }
}