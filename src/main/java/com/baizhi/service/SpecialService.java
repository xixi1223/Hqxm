package com.baizhi.service;

import com.baizhi.entity.Special;

import java.util.List;
import java.util.Map;

/**
 * Created by XIXI on 2019/11/27.
 */
public interface SpecialService {
    //分页
    Map<String,Object> findByPage(Integer page, Integer rows, String searchField, String searchString, String searchOper, Boolean _search);
    //总条数
    int NumCount(String searchField, String searchString, String searchOper);

    //条件搜索
    List<Special> findBySearch(String searchField,
                               String searchString,
                               String searchOper,
                               Integer page,
                               Integer rows);

    //添加
    Map add(Special special);
    //修改
    Map update(Special special);
    //删除
    Map delete(String id);
    //修改路径
    void updateUrl(Special special);

    //////////////////////接口///////////////////////////////
    List<Special> findAll();
    Special findOne(String id);
}


