package com.baizhi.controller;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.MapOV;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XIXI on 2019/12/2.
 */
@Controller
@RequestMapping("chart")
public class Chart {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    @RequestMapping("selectSex")
    @ResponseBody
    public Map<String,List> queryBySex(){
        //最近一天
        int count = userService.count("男", 1);
        int i = userService.count("女", 1);
        //最近七天
        int count1 = userService.count("男", 7);
        int i1 = userService.count("女", 7);
        //最近三十天
        int count2 = userService.count("男", 30);
        int i2 = userService.count("女", 30);
        //最近一年
        int count3 = userService.count("男", 365);
        int i3 = userService.count("女", 365);
        Map<String, List> map = new HashMap<>();
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> list1 = new ArrayList<>();
        list.add(count);
        list.add(count1);
        list.add(count2);
        list.add(count3);
        list1.add(i);
        list1.add(i1);
        list1.add(i2);
        list1.add(i3);
        map.put("man",list);
        map.put("woman",list1);
        return map;
    }

    @ResponseBody
    @RequestMapping("address")
    public  List<MapOV> findAddress(){
        List<MapOV> address = userService.findByAddress();
        return address;
    }
}
