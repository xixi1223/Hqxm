package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by XIXI on 2019/11/26.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Component
public class Admin implements Serializable {
    private String id;
    private String account;
    private String password;
}
