package com.baizhi.controller;

import com.baizhi.entity.Category;
import com.baizhi.entity.Video;
import com.baizhi.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("video")
public class VideoController {
    @Autowired
    private VideoService vs;
    //分页查
    @RequestMapping("quaryPage")
    public Map<String,Object> quaryPage(Integer page){
        Integer size=3;
        Map<String, Object> map = vs.quaryByPage(page, size);
        //返回当前页
        map.put("tablePage",page);
        return map;
    }
    //添加
    @RequestMapping("save")
    public void save(MultipartFile video,String title,String brief,String categoryId){
        //修改文件名
        String s = UUID.randomUUID().toString();
        //将参数封装成对象
        Video video1 = new Video(null, title, brief, null, s+video.getOriginalFilename(), null, new Category(categoryId,null,null,null), null, null);
        //调业务方法
        vs.insertVideo(video,video1);
    }
    //删除
    @RequestMapping("remove")
    public String remove(String id){
        vs.deleteById(id);
        return "删除成功";
    }

}
