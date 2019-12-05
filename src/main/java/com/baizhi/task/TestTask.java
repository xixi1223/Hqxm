package com.baizhi.task;

import com.baizhi.service.SlideshowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.util.Date;

/**
 * Created by XIXI on 2019/12/4.
 */
@Component
@Async
public class TestTask {
    @Autowired
    private SlideshowService slideshowService;
    @Scheduled(cron = "0 0 0 ? * SUN")
    public void task01() throws InterruptedException {
        try {
            slideshowService.daochu();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println("导出"+new Date());
    }
}
