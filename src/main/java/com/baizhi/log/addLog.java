package com.baizhi.log;


import com.baizhi.utils.LogUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by XIXI on 2019/12/2.
 */
@Aspect
@Configuration
public class addLog {
    @Autowired
    private HttpServletRequest request;
    @Around("@annotation(com.baizhi.utils.LogUtils)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint) throws Exception {
        HttpSession session = request.getSession();
        String who = (String) session.getAttribute("username");
        Date date=new Date();
        //获取注解信息
        MethodSignature methodSignature= (MethodSignature) proceedingJoinPoint.getSignature();
        LogUtils annotation = methodSignature.getMethod().getAnnotation(LogUtils.class);
        String method=annotation.value();
        String status=null;
        try {
            Object proceed = proceedingJoinPoint.proceed();
            status = "success";
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            status = "false";
            return null;
        }finally {
            String message="用户:"+who+"时间:"+date+"方法:"+method+"状态:"+status;
            String realPath = request.getSession().getServletContext().getRealPath("log.txt");
            FileInputStream os = new FileInputStream(realPath);
            InputStreamReader in = new InputStreamReader(os,"UTF-8");
            BufferedReader bu=new BufferedReader(in);
            List<String> list=new ArrayList<>();
            while (true){
                String line=bu.readLine();
                if(line==null) break;
                list.add(line);
            }
            in.close();
            FileOutputStream out = new FileOutputStream(realPath);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out,"UTF-8");
            BufferedWriter buffer = new BufferedWriter(outputStreamWriter);
            for(String k:list){
                buffer.write(k);
                buffer.newLine();
            }
            buffer.write(message);
            buffer.newLine();
            buffer.close();
        }
    }
}
