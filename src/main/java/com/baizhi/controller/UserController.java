package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by XIXI on 2019/12/4.
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    //登录接口
    @ResponseBody
    @RequestMapping("login")
    public Map login(String phone, String password) {
        HashMap<Object, Object> map = new HashMap<>();
        User user = userService.login(phone, password);
        String status="200";
        map.put("user",user);
        map.put("status",status);
        return map;
    }
    //注册
    @RequestMapping("registPhone")
    @ResponseBody
    public Map regisstId(String phone){
        HashMap<Object, Object> map = new HashMap<>();
        String s = userService.registUser(phone);
        String status="200";
        String message="成功";
        if(s.equals("") || s.equals(null)){
            status="-200";
            message="失败";
        }
        map.put("id",s);
        map.put("status",status);
        map.put("message",message);
        return map;
    }
    //添加详细信息
    @ResponseBody
    @RequestMapping("registUser")
    public Map registUser(String id,User user){
        HashMap<Object, Object> map = new HashMap<>();
        User user1 = userService.regist(id, user);
        String status="200";
        String message="成功";
        if(user1==null){
            status="-200";
            message="失败";
        }
        map.put("status",status);
        map.put("message",message);
        map.put("user",user1);
        return map;
    }

    //修改个人信息
    @RequestMapping("updateUser")
    @ResponseBody
    public Map updateUser(User user){
        HashMap<Object, Object> map = new HashMap<>();
        User user1 = userService.updateUser(user);
        String status="200";
        String message="成功";
        map.put("status",status);
        map.put("message",message);
        map.put("user1",user1);
        return map;
    }

}
