package com.baizhi.service;

import com.baizhi.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UserService {
    //分页
    Map<String,Object> quaryByPage(Integer page,Integer size);
    //查所有
    List<User> quaryAll();
    //修改状态
    void updateStatus(String id,Integer status);
    //添加
    void insertUser(MultipartFile photo,User user);
    //删除
    void deleteById(String id);
    //查一个
    User quaryById(String id);
    //查询男女生注册人数
    Map<String,Object> quaryCount();
}
