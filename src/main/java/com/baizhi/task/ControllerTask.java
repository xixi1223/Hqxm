package com.baizhi.task;

import com.baizhi.service.SlideshowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;

/**
 * Created by HIAPAD on 2019/12/3.
 */
@Component
public class ControllerTask {
    @Autowired
    private SlideshowService slideshowService;
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        return new ThreadPoolTaskScheduler();
    }
    public void run(){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // 业务代码
                try {
                    slideshowService.daochu();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        };
        threadPoolTaskScheduler.schedule(runnable,new CronTrigger("0/5 * * * * *"));
    }
    public void shutdown(){
        threadPoolTaskScheduler.shutdown();
    }
}
