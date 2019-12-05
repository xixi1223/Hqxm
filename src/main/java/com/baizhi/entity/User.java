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

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by XIXI on 2019/12/1.
 */
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@Component
public class User implements Serializable {
    @Id
    @KeySql(sql="select uuid()",order = ORDER.BEFORE)
    private String id;
    private String phone;
    private String password;
    private String salt;
    private String name;
    private String nick;
    private String sex;
    private String signature;
    private String headpic;
    private String address;
    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date createdate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Data lastlogin;
}
