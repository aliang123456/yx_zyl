package com.baizhi;

import com.baizhi.dao.VideoDao;
import com.baizhi.entity.Video;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class VideoDaoTest {
    @Autowired
    private VideoDao videoDao;
    @Test
    public void test1(){
        List<Video> videos = videoDao.selectRange(0, 2);
        for (Video video : videos) {
            System.out.println(video);
        }

    }

}
