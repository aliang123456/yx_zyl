package com.baizhi.service;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.annotation.DeleteCache;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.upload.Upload;
import com.baizhi.vo.MonthAndCount;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    //修改
    @DeleteCache
    @Override
    public void updateStatus(String id, Integer status) {
        userDao.updateStatus(id,status);
    }
    //添加
    @DeleteCache
    @Override
    public void insertUser(MultipartFile photo,User user) {
        try{
            //更改文件名
            String fileName= UUID.randomUUID().toString()+photo.getOriginalFilename();
            //调用OSS 字节数组文件上传
            Upload.uploadFile(photo,fileName);
            //存储图片路径
            user.setHeadimg("http://zyinliang.oss-cn-beijing.aliyuncs.com/aliang/"+fileName);
            //数据入库
            user.setId(UUID.randomUUID().toString());
            user.setCreatedate(new Date());
            user.setStatus(0);
            //调用dao
            userDao.insertUser(user);

            //实时数据更新
            Map<String, Object> map = quaryCount();
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-a4ec1b2676484c55b9c54012109ba376");
            goEasy.publish("aa", JSONObject.toJSONString(map));

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //删除
    @DeleteCache
    @Override
    public void deleteById(String id) {
        //先根据id 查一个
        User user = userDao.selectById(id);
        //获取数据库中存储的文件路径
        String headimg = user.getHeadimg();
        //获取指定字符下标
        int i = headimg.lastIndexOf("/");
        //根据指定下标截取文件名
        String substring = headimg.substring(i+1);
        //删除OSS云服务上存储的图片
        String objName="aliang/";//图片所在文件夹名
        Upload.deleteFile(substring,objName);
        //删除数据 调用dao
        userDao.deleteById(id);

        //实时更新数据
        Map<String, Object> map = quaryCount();
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-a4ec1b2676484c55b9c54012109ba376");
        goEasy.publish("aa", JSONObject.toJSONString(map));

    }
    //查一个
    @Override
    public User quaryById(String id) {
        //调用dao
        return userDao.selectById(id);
    }
    //查询男女生注册人数
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> quaryCount() {
        Map<String,Object> map=new HashMap<>();
        List<MonthAndCount> monthAndCounts = userDao.selectMonth("男");
        List<Integer> integers1 = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        for (MonthAndCount monthAndCount : monthAndCounts) {
            Integer month = monthAndCount.getMonth();
            Integer count = monthAndCount.getCount();
            integers1.set(month-1,count);
        }


        List<MonthAndCount> monthAndCounts1 = userDao.selectMonth("女");
        List<Integer> integers2 = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        for (MonthAndCount monthAndCount : monthAndCounts1) {
            Integer month = monthAndCount.getMonth();
            Integer count = monthAndCount.getCount();
            integers2.set(month-1,count);
        }
        List<String> list=new ArrayList<>();
        for (int i = 1; i < 12; i++) {
             list.add(i+"月");

        }

        map.put("date",list);
        map.put("nanCount",integers1);
        map.put("nvCount",integers2);
        return map;
    }

    //分页查
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> quaryByPage(Integer page, Integer size) {
        /*创建map集合  方便存储更多数据*/
        Map<String,Object> map=new HashMap<>();
        //调用dao
        /* 每页起始下标= 当前页数-1*每页展示条数 */
        List<User> users = userDao.selectRange((page - 1) * size, size);
        map.put("date",users);
        //调用dao 查总条数
        Integer integer = userDao.selectPageAll();
        //获取总页数
        map.put("pageAll",integer%size==0?integer/size:integer/size+1);
        return map;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<User> quaryAll() {

        return userDao.selectAll();
    }

}
