package com.baizhi.dao;

import com.baizhi.entity.Slideshow;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by XIXI on 2019/11/26.
 */
public interface SlideshowDao extends Mapper<Slideshow>,DeleteByIdListMapper<Slideshow,String>{
    //模糊总条数
    Integer count(@Param("searchField") String searchField,
                   @Param("searchString") String searchString,
                   @Param("searchOper") String searchOper);

    List<Slideshow> findBySearch(@Param("searchField") String searchField,
                                 @Param("searchString") String searchString,
                                 @Param("searchOper") String searchOper,
                                 @Param("page") Integer page,
                                 @Param("rows") Integer rows);
}


