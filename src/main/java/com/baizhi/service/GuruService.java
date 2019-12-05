package com.baizhi.service;

import com.baizhi.entity.Guru;

import java.util.List;
import java.util.Map;

/**
 * Created by XIXI on 2019/11/29.
 */
public interface GuruService {
    //分页
    Map<String, Object> searchAll(Integer page, Integer rows);

    //更新
    void update(Guru guru);

    //删除
    Map delete(String id);

    ////////////////上师接口////////////////////////////
    List<Guru> findAll();
}
