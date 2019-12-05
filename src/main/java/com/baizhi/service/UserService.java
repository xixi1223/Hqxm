package com.baizhi.service;

import com.baizhi.entity.MapOV;
import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by XIXI on 2019/12/1.
 */
public interface UserService {
     Map<String,Object> searchAll(Integer page, Integer rows) ;
     int count (String sex,Integer days);
     List<MapOV> findByAddress();


     //前台登录
     User login(String phone,String password);
     //注册
     String registUser(String phone);
     User regist(String id,User user);
     User updateUser(User user);

}
