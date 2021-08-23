package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("admin")
@CrossOrigin
@RestController
public class AdminController {

    @Autowired
    private AdminService as;
    @RequestMapping("quaryByName")
    public Map<String, Object> quaryByName(@RequestBody Admin admin){
        //调用业务
        Admin admin1 = as.quaryByName(admin.getUsername());
        //创建map
        Map<String, Object> map = new HashMap<>();
        map.put("log",false);
        //逻辑判断
        if(admin1!=null){
            //业务返回如果不为空判断密码
            if(admin1.getPassword().equals(admin.getPassword())){
                map.put("success",admin);
                map.put("log",true);
            }else {
                map.put("success","密码不正确");
            }
        }else {
            map.put("success","用户名不存在");
        }
        //返回结果
        return map;
    }

}
