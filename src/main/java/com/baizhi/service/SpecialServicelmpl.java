package com.baizhi.service;

import com.baizhi.dao.SpecialDao;
import com.baizhi.entity.Special;
import com.baizhi.utils.LogUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by XIXI on 2019/11/27.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class SpecialServicelmpl implements SpecialService {
    @Autowired
    private SpecialDao specialDao;
    @Autowired
    private  SpecialService specialService;


    @Override
    public Map<String, Object> findByPage(Integer page, Integer rows, String searchField, String searchString, String searchOper, Boolean _search) {
        Map<String,Object> map=new HashMap<>();
        List<Special> list=null;
        Integer total=0;
        Integer records=0;
        if(_search){
            list = specialService.findBySearch(searchField, searchString, searchOper, page, rows);
            records=specialService.NumCount(searchField,searchString,searchOper);
        }else {
            int pa=(page-1)*rows;
            list = specialDao.selectByRowBounds(new Special(),new RowBounds(pa,rows));
            records=specialDao.selectAll().size();
        }
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
    public int NumCount(String searchField, String searchString, String searchOper) {
        return specialDao.count(searchField,searchString,searchOper);
    }

    @Override
    public List<Special> findBySearch(String searchField, String searchString, String searchOper, Integer page, Integer rows) {
        int pa=(page-1)*rows;
        return specialDao.findBySearch(searchField,searchString,searchOper,pa,rows);
    }

    @LogUtils("添加编辑")
    @Override
    public Map add(Special special) {
        HashMap hashMap = new HashMap();
        String uuid= UUID.randomUUID().toString();
        special.setId(uuid);
        specialDao.insert(special);
        hashMap.put("specialId", uuid);
        hashMap.put("status", 200);
        return hashMap;
    }

    @LogUtils("更新编辑")
    @Override
    public Map update(Special special) {
        HashMap hashMap = new HashMap();
        specialDao.updateByPrimaryKeySelective(special);
        hashMap.put("status",200);
        hashMap.put("specialId", special.getId());
        hashMap.put("message","更新成功");
        return hashMap;
    }

    @LogUtils("删除编辑")
    @Override
    public Map delete(String id) {
        HashMap hashMap = new HashMap();
        Special special = new Special();
        special.setId(id);
        specialDao.delete(special);
        hashMap.put("status",200);
        return hashMap;
    }

    @Override
    public void updateUrl(Special special) {
        specialDao.updateByPrimaryKeySelective(special);
    }

    @Override
    public List<Special> findAll() {
        return specialDao.selectAll();
    }

    @Override
    public Special findOne(String id) {
        return specialDao.selectOne(new Special().setId(id));
    }
}
