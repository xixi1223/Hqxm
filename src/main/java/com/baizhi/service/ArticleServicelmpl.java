package com.baizhi.service;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XIXI on 2019/12/1.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ArticleServicelmpl implements  ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Override
    public Map<String, Object> searchAll(Integer page, Integer rows) {
        Map<String,Object> map=new HashMap<>();
        List<Article> list=null;
        Integer total=0;
        Integer records=0;

        int pa=(page-1)*rows;
        list = articleDao.selectByRowBounds(new Article(),new RowBounds(pa,rows));
        records=articleDao.selectAll().size();

        if((records%rows)==0){
            total=records/rows;
        }else{
            total=(records/rows)+1;
        }
        map.put("rows",list);
        map.put("page",page);
        map.put("total",total);
        map.put("records",records);
        return map;
    }

    @Override
    public List<Article> finaAll() {
        return articleDao.selectAll();
    }

    @Override
    public Article findOne(String id) {
        return articleDao.selectOne(new Article().setId(id));
    }
}
