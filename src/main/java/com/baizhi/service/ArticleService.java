package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.List;
import java.util.Map;

/**
 * Created by XIXI on 2019/12/1.
 */
public interface ArticleService {
    //分页
    Map<String, Object> searchAll(Integer page, Integer rows);

    /////////////////////////接口//////////////////////////////////
    //查询所有
    List<Article> finaAll();
    //文章详情接口
    Article findOne(String id);
}
