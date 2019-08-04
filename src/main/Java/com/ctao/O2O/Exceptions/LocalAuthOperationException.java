package com.ctao.O2O.Exceptions;

public class LocalAuthOperationException extends RuntimeException {
    private static final long serialVersionUID = -4923291018874582037L;

    public  LocalAuthOperationException(String msg){
        super(msg);
    }
}
