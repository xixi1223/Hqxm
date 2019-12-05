package com.baizhi.controller;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by XIXI on 2019/11/26.
 */
@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminDao adminDao;

    @RequestMapping("login")
    @ResponseBody
    public String login(String username,String password,String yzm, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("username",username);
        System.out.println(username+123);
        String securitycode = (String) session.getAttribute("securitycode");
        Admin admin = adminDao.selectOne(new Admin(null, username, null));
        if(admin==null){
            return "账户不正确";
        }else if(!password.equals(admin.getPassword())){
            return "密码不正确";
        }else if(!yzm.equals(securitycode)){
           return "验证码不正确";
        } else {
           return null;
        }
    }
}
