package com.baizhi.service;

import com.baizhi.annotation.DeleteCache;
import com.baizhi.dao.CategoryDao;
import com.baizhi.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao cd;
    //根据类别查询
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> quaryLevels(Integer levels) {

        return cd.selectLevels(levels);

    }
    //根据一级类别查询一级类别下所有二级类别
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> quaryByParent(String parentId) {

        return cd.selectByParent(parentId);
    }
    //添加类别
    @DeleteCache
    @Override
    public void insertCate(Category category) {
        //id自动生成uuid
        category.setId(UUID.randomUUID().toString());
        cd.insertCate(category);
    }
    //删除
    @DeleteCache
    @Override
    public Map<String, Object> deleteById(String id) {
        Map<String,Object> map=new HashMap<>();
        map.put("flg",true);
        //先根据id查询
        Category category = cd.selectById(id);
        //判断级别
        if(category.getLevels()!=1){
            //二级类别直接删除
            cd.deleteById(id);
        }else{
            //根据一级类别查询当前类别下所有二级类别
            List<Category> categories = cd.selectByParent(id);
            System.out.println(categories.size());
            //判断当前一级类别下是否有二级类别
            if(categories.size()==0){
                //没有删除一级类别
                cd.deleteById(id);
            }else{
                //有  不删
                map.put("flg",false);
                map.put("rmg","当前类别下有内容，无法删除");
            }
        }
        return map;
    }
    //查一个
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Category quaryById(String id) {
        return cd.selectById(id);
    }
}
