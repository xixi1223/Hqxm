package com.baizhi.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by XIXI on 2019/11/26.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
@HeadRowHeight(20)
@ColumnWidth(30)
@ContentRowHeight(50)
public class Slideshow implements Serializable {

    @Id
    @KeySql(sql="select uuid()",order = ORDER.BEFORE)
    @ExcelProperty("图片ID")
    private String id;
    @ExcelProperty("图片名字")
    private String name;
    @ExcelIgnore
    private String path;
    @ExcelProperty("图片简介")
    private String introduction;
    @ExcelIgnore
    private String href;
    @ExcelProperty("图片状态")
    private String status;
}
