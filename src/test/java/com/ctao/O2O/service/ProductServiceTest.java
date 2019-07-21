package com.ctao.O2O.service;

import com.ctao.O2O.BaseTest;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;
}
