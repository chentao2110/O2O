package com.ctao.O2O.Exceptions;

import java.io.Serializable;

public class ShopOperationException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 2501021346177385460L;

    public ShopOperationException(String msg){
        super(msg);
    }
}
