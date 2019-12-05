package com.baizhi.controller;

import com.baizhi.utils.SecurityCode;
import com.baizhi.utils.SecurityImage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * Created by XIXI on 2019/11/26.
 */
@Controller
@RequestMapping("admin")
public class YzmController {

    @RequestMapping("YanZheng")
    public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //生成验证码的随机数
        String securityCode = SecurityCode.getSecurityCode();
        HttpSession session = request.getSession();
        session.setAttribute("securitycode", securityCode);
        //绘制生成验证码的图片
        BufferedImage Image = SecurityImage.createImage(securityCode);
        //响应到客户端
        ServletOutputStream output= response.getOutputStream();
			/*
			 * 第一个参数：指定验证码图片的对象
			 * 第二个参数：图片得到格式
			 * 第三个参数：指定输出流
			*/
        ImageIO.write(Image, "png", output);

        return null;

    }
}
