package com.baizhi.controller;

import com.baizhi.service.VideoService;
import com.baizhi.vo.VideoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("yingx/app")
public class AppController {

    @Autowired
    private VideoService vs;

    @RequestMapping("queryByReleaseTime")
    public Map<String, Object> queryByReleaseTime(){
        Map<String,Object> map=new HashMap<>();
        List<VideoVo> videoVos = vs.quaryAll();
        map.put("data",videoVos);
        return map;
    }

}
