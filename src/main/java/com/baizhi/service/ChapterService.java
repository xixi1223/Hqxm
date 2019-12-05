package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.List;
import java.util.Map;

/**
 * Created by XIXI on 2019/11/28.
 */
public interface ChapterService {
    //分页
    Map<String, Object> findByPage(Integer page, Integer rows, String Sid);

    //添加
    Map add(Chapter chapter);

    //修改
    Map update(Chapter chapter);

    //删除
    Map delete(String id);

    //修改路径
    void updateUrl(Chapter chapter);

    //////////////////////接口////////////////////////////
    List<Chapter> findById(String id);
}
