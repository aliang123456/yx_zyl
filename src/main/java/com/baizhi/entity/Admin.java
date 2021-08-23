package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data //生成get set toString
@AllArgsConstructor //生成全参构造
@NoArgsConstructor  //生成无参构造
public class Admin implements Serializable {

  private String id;
  private String username;
  private String password;
  private long status;


}
