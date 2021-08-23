package com.baizhi.service;

import com.baizhi.entity.Video;
import com.baizhi.vo.VideoVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface VideoService {

    //分页
    Map<String,Object> quaryByPage(Integer page, Integer size);

    //添加
    void insertVideo(MultipartFile file, Video video);

    //删除
    void deleteById(String id);

    //根据视频上传时间降序排序
    List<VideoVo> quaryAll();
}
