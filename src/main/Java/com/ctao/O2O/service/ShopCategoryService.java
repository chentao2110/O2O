package com.ctao.O2O.service;

import com.ctao.O2O.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
    List<ShopCategory> getCategoryList(ShopCategory shopCategory);
}
