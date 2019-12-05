package com.baizhi.service;

import com.alibaba.excel.EasyExcel;
import com.baizhi.dao.SlideshowDao;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Slideshow;
import com.baizhi.utils.LogUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by XIXI on 2019/11/27.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class SlideshowServicelmpl implements SlideshowService
{
    @Autowired
    private SlideshowDao slideshowDao;
    @Autowired
    private SlideshowService slideshowService;

    @Override
    public Map<String, Object> findByPage(Integer page, Integer rows, String searchField, String searchString, String searchOper, Boolean _search) {
        Map<String,Object> map=new HashMap<>();
        List<Slideshow> list=null;
        Integer total=0;
        Integer records=0;
        if(_search){
            list = slideshowService.findBySearch(searchField, searchString, searchOper, page, rows);
            records=slideshowService.NumCount(searchField,searchString,searchOper);
        }else {
            int pa=(page-1)*rows;
            list = slideshowDao.selectByRowBounds(new Slideshow(),new RowBounds(pa,rows));
            records=slideshowDao.selectAll().size();
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
        return slideshowDao.count(searchField,searchString,searchOper);
    }

    @Override
    public List<Slideshow> findBySearch(String searchField, String searchString, String searchOper, Integer page, Integer rows) {
        int pa=(page-1)*rows;
        return slideshowDao.findBySearch(searchField,searchString,searchOper,pa,rows);
    }


    @LogUtils("添加轮播图")
    @Override
    public Map add(Slideshow slideshow) {
        HashMap hashMap = new HashMap();
        String uuid= UUID.randomUUID().toString();
        slideshow.setId(uuid);
        slideshowDao.insert(slideshow);
        hashMap.put("slideshowId", uuid);
        hashMap.put("status", 200);
        return hashMap;
    }

    @LogUtils("更新轮播图")
    @Override
    public Map update(Slideshow slideshow) {
        HashMap hashMap = new HashMap();
        slideshowDao.updateByPrimaryKeySelective(slideshow);
        hashMap.put("status",200);
        hashMap.put("slideshowId", slideshow.getId());
        hashMap.put("message","更新成功");
        return hashMap;
    }

    @LogUtils("删除轮播图")
    @Override
    public Map delete(String [] id) {
        HashMap hashMap = new HashMap();
        slideshowDao.deleteByIdList(Arrays.asList(id));
        hashMap.put("status",200);
        hashMap.put("message","删除成功");
        return hashMap;
    }

    @Override
    public void updateUrl(Slideshow slideshow) {
        slideshowDao.updateByPrimaryKeySelective(slideshow);
    }

    @Override
    public void daochu() throws MalformedURLException {
        List<Slideshow> slideshows = slideshowDao.selectAll();
        ArrayList<Banner> list = new ArrayList<>();
        for(Slideshow s:slideshows){
            Banner banner = new Banner();
            banner.setId(s.getId());
            banner.setName(s.getName());
            banner.setIntroduction(s.getIntroduction());
            banner.setStatus(s.getStatus());
            banner.setUrl(new URL(s.getPath()));
            list.add(banner);
        }
        String fileName = "C:\\Users\\XIXI\\Desktop\\hqxm\\"+new Date().getTime()+"DemoData.xlsx";
        EasyExcel.write(fileName,Banner.class).sheet("轮播图导出信息").doWrite(list);
    }

    /////////////////////////////////////////////////////
    @Override
    public List<Slideshow> finaAll() {
        Slideshow slideshow = new Slideshow();
        slideshow.setStatus("1");
        return slideshowDao.select(slideshow);
    }
}
