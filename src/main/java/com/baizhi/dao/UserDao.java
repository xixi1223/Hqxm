package com.baizhi.dao;

import com.baizhi.entity.MapOV;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by XIXI on 2019/12/1.
 */
public interface UserDao extends Mapper<User> {
    int queryBySex(
            @Param("sex") String sex,
            @Param("day") Integer day);

     List<MapOV> queryAddress();
}
