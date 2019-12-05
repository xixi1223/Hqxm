package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by XIXI on 2019/11/29.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Component
public class Guru implements Serializable{
    @Id
    @KeySql(sql="select uuid()",order = ORDER.BEFORE)
    private String id;
    private String name;
    private String monastic;
    private String headpic;
}
