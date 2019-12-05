package com.baizhi.service;

import com.baizhi.entity.Slideshow;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

/**
 * Created by XIXI on 2019/11/27.
 */
public interface SlideshowService {
    //分页查询
    Map<String,Object> findByPage(Integer page, Integer rows,
                                  String searchField, String searchString, String searchOper, Boolean _search);
    //总条数
    int NumCount(String searchField, String searchString, String searchOper);

    //条件搜索
    List<Slideshow> findBySearch(String searchField,
                                 String searchString,
                                 String searchOper,
                                 Integer page,
                                 Integer rows);

    //添加
    Map add(Slideshow slideshow);
    //修改
    Map update(Slideshow slideshow);
    //删除
    Map delete(String[] id);
    //修改路径
    void updateUrl(Slideshow slideshow);

    //导出轮播图
    void daochu() throws MalformedURLException;

///////////////////////////////接口///////////////////////////////////////////////////
    //查询所有
    List<Slideshow>finaAll();

}
