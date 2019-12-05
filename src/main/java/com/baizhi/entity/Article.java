package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by XIXI on 2019/11/27.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Component
public class Article implements Serializable {
    @Id
    @KeySql(sql = "select uuid()", order = ORDER.BEFORE)
    private String id;
    private String title;
    private String content;
    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date createdate;
    private String author;
    private String cover;
    @Column(name="grguId")
    private String grguId;
}
