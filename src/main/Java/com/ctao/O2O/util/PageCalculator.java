package com.ctao.O2O.util;

public class PageCalculator {
    public static int rowIndexCalculator(int pageIndex , int pageSize){
        return (pageIndex-1 > 0)?(pageIndex-1)*pageSize : 0;
    }
}
