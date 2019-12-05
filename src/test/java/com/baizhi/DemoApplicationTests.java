package com.baizhi;

import com.alibaba.excel.EasyExcel;
import com.baizhi.dao.AdminDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.dao.SlideshowDao;
import com.baizhi.entity.*;
import com.baizhi.service.GuruService;
import com.baizhi.service.SlideshowService;
import com.baizhi.service.UserService;
import com.baizhi.utils.SmsSample;
import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.util.*;

@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
public class DemoApplicationTests {

	@Autowired
	private AdminDao adminDao;
	@Autowired
	private SlideshowDao slideshowDao;
	@Autowired
	private SlideshowService slideshowService;
	@Autowired
	private ChapterDao chapterDao;
	@Autowired
	private GuruService guruService;
	@Autowired
	private UserService userService;


	@Test
	public void testAddress(){
		HashMap<String,Integer> map = new HashMap<>();
		List<MapOV> address = userService.findByAddress();
		for (MapOV ov : address) {
			map.put(ov.getName(),ov.getValue());
		}
		System.out.println(map);
	}
	@Test
	public void testAdd(){
		List<MapOV> address = userService.findByAddress();
		System.out.println(address);
	}
	@Test
	public void testCount(){
		int i = userService.count("女", 360);
		System.out.println(i);

	}
	@Test
	public void testUser(){
		Map<String, Object> stringObjectMap = userService.searchAll(1, 2);
		System.out.println(stringObjectMap);
	}

	@Test
	public void testGuru(){
		Map<String, Object> stringObjectMap = guruService.searchAll(1, 2);
		System.out.println(stringObjectMap);
	}

	@Test
   public  void testContextLoads() {
		List<Admin> admins = adminDao.selectAll();
		admins.forEach(k-> System.out.println(k));
	}

	//登录
	@Test
	public void testLogin(){
		Admin admin = adminDao.selectOne(new Admin(null, "admin", null));
		System.out.println(admin);
	}

	//轮播图
	@Test
	public void testSlide(){
		List<Slideshow> slideshows = slideshowDao.selectAll();
		slideshows.forEach(k-> System.out.println(k));
	}

	//条件查询
	@Test
	public void testTiao(){
		List<Slideshow> list = slideshowService.findBySearch("introduction","你","cn",1,2);
		list.forEach(s-> System.out.println(s));

		int i = slideshowService.NumCount("introduction", "你", "cn");
		System.out.println(i);
	}

	@Test
	public void testOne(){
		Slideshow slideshow1 = new Slideshow();
		slideshow1.setId("8c48d9f9-b433-492e-bf06-2d7517137b55");
		Slideshow slideshow = slideshowDao.selectOne(slideshow1);
		System.out.println(slideshow);
	}

	@Test
	public void testCheapter(){
		List<Chapter> select = chapterDao.select(new Chapter(null, null, null, null, null, "2"));
		select.forEach(j-> System.out.println(j));
	}

	@Test
	public void testCh(){
		List<Chapter> chapters = chapterDao.selectAll();
		for (Chapter chapter : chapters) {
			System.out.println(chapter+"11");
			String[] split = chapter.getHref().split("/");
				System.out.println(split[5]);
		}
	}

	@Test
    public void TestPic(){
        List<Slideshow> slideshows = slideshowDao.selectAll();
        System.out.println(slideshows.size());
    }

@Test
public void daochu() throws Exception {
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
    System.out.println(list);
    String fileName = "C:\\Users\\XIXI\\Desktop\\hqxm\\"+new Date().getTime()+"DemoData.xlsx";
	EasyExcel.write(fileName,Banner.class).sheet("轮播图导出信息").doWrite(list);
}

@Test
	public void testGoeasy(){
	GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-36e72a6f73ca455b830f7e36639c7a5f");
			goEasy.publish("abcde", "Hello, GoEasy!");
}


@Test
	public void testMessage(){
	int i= (int) (Math.random()*1000000+1);
	System.out.println(i);
	SmsSample smsSample = new SmsSample();
	smsSample.SendMessage("15978520120",i);
}




}
