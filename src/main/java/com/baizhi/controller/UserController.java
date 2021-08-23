package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSONObject;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.upload.Upload;
import io.goeasy.GoEasy;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService us;
    //分页
    @RequestMapping("quaryByPage")
    public Map<String,Object> quaryByPage(Integer page){
        //设置每页显示条数
        Integer size=3;

        //调用业务
        //当前页数和展示条数
        Map<String, Object> map = us.quaryByPage(page, size);
        //当前页数
        map.put("page",page);
        return map;
    }
    //修改
    @RequestMapping("updateStatus")
    public String updateStatus(@RequestBody User user){
        us.updateStatus(user.getId(),user.getStatus());
        return "修改成功";
    }
    //添加
    @RequestMapping("add")
    public String add(MultipartFile photo,String username,String phone,String brief,String radio) throws IOException {
        User user=new User(null,username,phone,null,brief,null,null,null,radio);
        us.insertUser(photo,user);
        return "修改成功";
    }
    //删除
    @RequestMapping("remove")
    public String remove(String id){
        us.deleteById(id);
        return "修改成功";
    }
    //导出用户信息
    @RequestMapping("download")
    public String downloadUser(){
        List<User> user = us.quaryAll();

        for (User us : user) {
            String headimg = us.getHeadimg();
            //根据指定下标截取文件名
            String[] split = headimg.split("/");
            Upload.download(split[split.length - 1]);
            us.setHeadimg("D:\\IDEACodes\\yx_zyl\\src\\main\\webapp\\uplod\\"+split[split.length - 1]);
        }

        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook =null;
        try{
            workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息","用户"),User.class, user);

            workbook.write(new FileOutputStream(new File("E:/downloadUser.xls")));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    //查询男女生注册人数
    @RequestMapping("echarts")
    public Map<String, Object> echarts(){
        Map<String, Object> map = us.quaryCount();
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-a4ec1b2676484c55b9c54012109ba376");
        goEasy.publish("aa", JSONObject.toJSONString(map));
        return map;
    }

}

