package com.ctao.O2O.Exceptions;

public class PersonOperationException extends RuntimeException {
    private static final long serialVersionUID = -6771745161996310703L;

    public PersonOperationException(String msg){
        super(msg);
    }
}
