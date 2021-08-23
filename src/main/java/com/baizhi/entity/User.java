package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
  @Excel(name = "用户id")
  private String id;
  @Excel(name = "用户姓名")
  private String name;
  @Excel(name = "用户电话")
  private String phone;
  @Excel(name = "用户头像",type = 2)
  private String headimg;
  @Excel(name = "用户介绍")
  private String brief;
  @Excel(name = "用户微信")
  private String wechat;
  @Excel(name = "用户生日",format = "yyyy年MM月dd日")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date createdate;
  @Excel(name = "用户账号状态")
  private Integer status;
  @Excel(name = "用户性别")
  private String sex;

}
