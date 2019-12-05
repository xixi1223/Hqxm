package com.baizhi.service;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
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
 * Created by XIXI on 2019/11/28.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ChapterServicelmpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;

    @Override
    public Map<String, Object> findByPage(Integer page, Integer rows,String Sid) {
        Map<String,Object> map=new HashMap<>();
        List<Chapter> list=null;
        Integer total=0;
        Integer records=0;

        int pa=(page-1)*rows;
        list = chapterDao.selectByRowBounds(new Chapter().setSpecialId(Sid),new RowBounds(pa,rows));
        records=chapterDao.selectAll().size();

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

    @LogUtils("添加章节")
    @Override
    public Map add(Chapter chapter) {
        HashMap hashMap = new HashMap();
        String uuid= UUID.randomUUID().toString();
        chapter.setId(uuid);
        chapterDao.insert(chapter);
        hashMap.put("chapterId", uuid);
        hashMap.put("status", 200);
        return hashMap;
    }

    @LogUtils("更新章节")
    @Override
    public Map update(Chapter chapter) {
        HashMap hashMap = new HashMap();
        chapterDao.updateByPrimaryKeySelective(chapter);
        hashMap.put("status",200);
        hashMap.put("chapterId", chapter.getId());
        hashMap.put("message","更新成功");
        return hashMap;
    }

    @LogUtils("删除章节")
    @Override
    public Map delete(String id) {
        HashMap hashMap = new HashMap();
        Chapter chapter = new Chapter();
        chapter.setId(id);
        chapterDao.delete(chapter);
        hashMap.put("status",200);
        return hashMap;
    }

    @Override
    public void updateUrl(Chapter chapter) {
        chapterDao.updateByPrimaryKeySelective(chapter);
    }

    @Override
    public List<Chapter> findById(String id) {
        return chapterDao.select(new Chapter().setSpecialId(id));
    }
}
