package com.baizhi.controller;

import com.baizhi.entity.Category;
import com.baizhi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService cs;
    //按级别查询
    @RequestMapping("queryByLevels")
    public List<Category> queryByLevels(Integer levels){
        //调用业务
        List<Category> categories = cs.quaryLevels(levels);
        return categories;
    }
    //查询一级类别下所有二级类别
    @RequestMapping("queryByParent")
    public List<Category> queryByParent(String id){
        //调用业务
        List<Category> categories = cs.quaryByParent(id);
        return categories;
    }
    //添加类别
    @RequestMapping("save")
    public Map<String, Object> save(@RequestBody Category category){
        Map<String,Object> map=new HashMap<>();
        cs.insertCate(category);
        //查询当前一级类别下的二级类别
        List<Category> categories = cs.quaryByParent(category.getParentId());
        //查询所有一级类别
        List<Category> categories1 = cs.quaryLevels(1);
        //存储map集合
        map.put("acc",categories);
        map.put("abb",categories1);
        return map;
    }
    //删除
    @RequestMapping("delete")
    public Map<String, Object> delete(String id){
        //调用业务
        return cs.deleteById(id);
    }

    @RequestMapping("queryById")
    public Category queryById(String id){
        return cs.quaryById(id);
    }
}
