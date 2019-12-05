package com.baizhi.controller;

import com.baizhi.entity.*;
import com.baizhi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XIXI on 2019/12/4.
 */
@Controller
@RequestMapping("shouye")
public class ShouYeController {

    //轮播图
    @Autowired
    private SlideshowService slideshowService;
    //文章
    @Autowired
    private ArticleService articleService;
    //专辑
    @Autowired
    private SpecialService specialService;
    @Autowired
    private ChapterService chapterService;
    //上师
    @Autowired
    private GuruService guruService;

    //首页展示
    @RequestMapping("ShouYeEshow")
    @ResponseBody
    public Map show(){
        List<Slideshow>  ss= slideshowService.finaAll();
        //随机选取5张
        List<Slideshow> slideshows=ss.subList(0,5);
        List<Article> articles = articleService.finaAll();
        List<Special> specials = specialService.findAll();
        HashMap<Object, Object> map = new HashMap<>();
        map.put("head",slideshows);
        map.put("atticles",articles);
        map.put("specials",specials);
        return map;
    }

    //文章详情
    @ResponseBody
    @RequestMapping("articleDetail")
    public Article findOne(String id){
        return articleService.findOne(id);
    }

    //专辑详情
    @RequestMapping("specialDetail")
    @ResponseBody
    public Map specialOne(String id){
        Special one = specialService.findOne(id);
        List<Chapter> chapters = chapterService.findById(id);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("special",one);
        map.put("chapters",chapters);
        return map;
    }

    //展示上师
    @ResponseBody
    @RequestMapping("showGuru")
    public List<Guru> findAllGuru(){
        return guruService.findAll();
    }

}
