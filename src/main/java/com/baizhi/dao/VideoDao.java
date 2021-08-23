package com.baizhi.dao;

import com.baizhi.entity.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoDao {
    //分页
    List<Video> selectRange(@Param("start") Integer start,@Param("end") Integer end);

    //查总条数
    Integer selectPageAll();

    //添加
    void insertVideo(Video video);

    //删除
    void deleteById(String id);

    //根据id查一个
    Video selectById(String id);

}
