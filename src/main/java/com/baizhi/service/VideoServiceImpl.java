package com.baizhi.service;

import com.baizhi.annotation.DeleteCache;
import com.baizhi.dao.VideoDao;
import com.baizhi.dao.VideoVoDao;
import com.baizhi.entity.Video;
import com.baizhi.upload.Upload;
import com.baizhi.vo.VideoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.midi.Soundbank;
import java.util.*;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoDao vd;
    @Autowired
    private VideoVoDao vvd;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> quaryByPage(Integer page, Integer size) {
        Map<String,Object> map=new HashMap<>();
        //没也开始条数是 当前页数减一乘每页展示条数
        List<Video> videos = vd.selectRange((page - 1) * size, size);
        //存储map集合中
        map.put("videos",videos);
        //调用dao查总条数
        Integer integer = vd.selectPageAll();
        //计算总页数
        map.put("pageAll",integer%size==0?integer/size:integer/size+1);
        return map;
    }
    //添加
    @DeleteCache
    @Override
    public void insertVideo(MultipartFile file, Video video) {
        try{
            //文件名
            String fileName = video.getVideoPath();
            //上传
            Upload.uploadFile(file,fileName);
            Upload.videoFile(file,fileName);
            //存储 id UUID
            video.setId(UUID.randomUUID().toString());
            //存储视频网络路径
            video.setVideoPath("http://zyinliang.oss-cn-beijing.aliyuncs.com/aliang/"+fileName);
            //视频后缀改为照片后缀
            String[] split = fileName.split("\\.");
            //第一帧照片网络路径
            video.setCoverPath("http://zyinliang.oss-cn-beijing.aliyuncs.com/video/"+split[0]+".jpg");
            //上传时间
            video.setCreateDate(new Date());
            //调用dao
            vd.insertVideo(video);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //删除
    @DeleteCache
    @Override
    public void deleteById(String id) {
        String objName1="aliang/";//视频所在文件夹名
        String objName2="video/";//图片所在文件夹名
        //先查一个
        Video video = vd.selectById(id);
        //视频
        String videoPath = video.getVideoPath();
        //获取指定字符下标
        int i = videoPath.lastIndexOf("/");
        //根据指定下标截取文件名
        String substring = videoPath.substring(i+1);
        //删除视频
        Upload.deleteFile(substring,objName1);

        //图片
        String coverPath = video.getCoverPath();
        //根据指定下标截取文件名
        String[] split = coverPath.split("/");
        //改为图片后缀  删除图片
        Upload.deleteFile(split[split.length-1],objName2);
        //删除数据
        vd.deleteById(id);
    }

    @Override
    public List<VideoVo> quaryAll() {
        return vvd.selectAll();
    }
}
