package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by XIXI on 2019/11/27.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Component
public class Chapter implements Serializable {
    @Id
    @KeySql(sql="select uuid()",order = ORDER.BEFORE)
    private String id;
    private String name;
    private String sizez;
    //时长
    private String duration;
    private String href;
    @Column(name = "specialId")
    private String specialId;
}
