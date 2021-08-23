package com.baizhi;

import com.baizhi.dao.AdminDao;
import com.baizhi.dao.UserDao;
import com.baizhi.dao.VideoDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Video;
import com.baizhi.service.AdminService;
import com.baizhi.service.VideoService;
import com.baizhi.vo.MonthAndCount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class YxZylApplicationTests {

    @Autowired
    private AdminDao adminDao;
    @Autowired
    private VideoDao videoDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private VideoService vs;

    @Autowired
    private AdminService adminService;

    @Test
    void contextLoads() {
        Admin zyl = adminDao.selectByName("zyl");
        System.out.println(zyl);
    }

    @Test
    public void testService(){
        Admin zyl = adminDao.selectByName("zyl");
        System.out.println(zyl);
    }

    @Test
    public void test22(){
        List<Video> videos = videoDao.selectRange(0, 2);
        for (Video video : videos) {
            System.out.println(video);
        }

    }

    @Test
    public void test2222(){

        Map<String,Object> map=new HashMap<>();
        List<MonthAndCount> monthAndCounts = userDao.selectMonth("男");
        boolean b=false;
        for (int i=1;i<=12;i++){
            for (MonthAndCount monthAndCount : monthAndCounts) {
                if(monthAndCount.getMonth()!=i){
                    b=true;
                }
            }
            if(b){
                monthAndCounts.add(new MonthAndCount(i,0));
            }
        }
        for (MonthAndCount monthAndCount : monthAndCounts) {
            System.out.println(monthAndCount);
        }
    }

}
