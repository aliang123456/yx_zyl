package com.baizhi.service;


import com.baizhi.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    //根据级别查询所有类别
    List<Category> quaryLevels(Integer levels);

    //查询所有二级类别
    List<Category> quaryByParent(String parentId);

    //添加类别
    void insertCate(Category category);

    //删除
    Map<String,Object> deleteById(String id);

    //查一个
    Category quaryById(String id);

}
