package com.baizhi.service;

import com.baizhi.dao.GuruDao;
import com.baizhi.entity.Guru;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XIXI on 2019/11/29.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class GuruServicelmpl implements GuruService {
    @Autowired
    private GuruDao guruDao;

    @Override
    public Map<String, Object> searchAll(Integer page, Integer rows) {
        Map<String,Object> map=new HashMap<>();
        List<Guru> list=null;
        Integer total=0;
        Integer records=0;

        int pa=(page-1)*rows;
        list = guruDao.selectByRowBounds(new Guru(),new RowBounds(pa,rows));
        records=guruDao.selectAll().size();

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
    public void update(Guru guru) {
        guruDao.updateByPrimaryKeySelective(guru);
    }

    @Override
    public Map delete(String id) {
        HashMap hashMap = new HashMap();
        Guru guru = new Guru();
        guru.setId(id);
        guruDao.delete(guru);
        hashMap.put("status",200);
        return hashMap;
    }

    @Override
    public List<Guru> findAll() {
        return guruDao.selectAll();
    }
}
