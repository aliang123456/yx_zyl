package com.baizhi.dao;

import com.baizhi.entity.User;
import com.baizhi.vo.MonthAndCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    //分页
    List<User> selectRange(@Param("start") Integer start,@Param("end") Integer end);
    //查所有
    List<User> selectAll();
    //修改状态
    void updateStatus(@Param("id") String id,@Param("status") Integer status);
    //查总条数
    Integer selectPageAll();
    //添加
    void insertUser(User user);
    //删除
    void deleteById(String id);
    //查一个
    User selectById(String id);
    //查询每月注册人数
    List<MonthAndCount> selectMonth(String sex);
}
