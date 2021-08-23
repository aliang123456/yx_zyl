package com.baizhi.dao;

import com.baizhi.entity.Category;

import java.util.List;

public interface CategoryDao {
    //根据级别查询所有类别
    List<Category> selectLevels(Integer levels);

    //根据一级类别查询所有二级类别
    List<Category> selectByParent(String parentId);

    //添加类别
    void insertCate(Category category);

    //删除
    void deleteById(String id);

    //查一个
    Category selectById(String id);

    //修改
    void updateById(String id);

}
