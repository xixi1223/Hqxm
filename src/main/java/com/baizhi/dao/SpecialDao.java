package com.baizhi.dao;

import com.baizhi.entity.Special;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by XIXI on 2019/11/27.
 */
public interface SpecialDao extends Mapper<Special> {
    //模糊总条数
    Integer count(@Param("searchField") String searchField,
                  @Param("searchString") String searchString,
                  @Param("searchOper") String searchOper);

    List<Special> findBySearch(@Param("searchField") String searchField,
                                 @Param("searchString") String searchString,
                                 @Param("searchOper") String searchOper,
                                 @Param("page") Integer page,
                                 @Param("rows") Integer rows);
}
