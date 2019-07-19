package com.ctao.O2O.Exceptions;

import java.io.Serializable;

public class ProductOperationException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 3904730631163339050L;

    public ProductOperationException(String msg){
        super(msg);
    }
}
