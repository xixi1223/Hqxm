package com.baizhi.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.net.URL;

/**
 * Created by XIXI on 2019/12/3.
 */
@Data
@Component
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Banner extends Slideshow {
    @ExcelProperty("图片")
    private URL url;

}
