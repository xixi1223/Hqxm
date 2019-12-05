package com.baizhi.controller;

import com.baizhi.dao.*;
import com.baizhi.entity.*;
import com.baizhi.service.*;
import com.baizhi.utils.HttpUtil;
import com.baizhi.utils.LogUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by XIXI on 2019/11/26.
 */
@RequestMapping("back")
@Controller
public class BackController {

    @Autowired
    private SlideshowService slideshowService;
    @Autowired
    private SlideshowDao slideshowDao;
    @Autowired
    private SpecialDao specialDao;
    @Autowired
    private SpecialService specialService;
    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private GuruService guruService;
    @Autowired
    private GuruDao guruDao;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;


    ///////////////////////////////////轮播图////////////////////////////////////////////

    @RequestMapping("AllSlideshow")
    @ResponseBody
    public Map<String,Object> findByPage(Integer page,Integer rows,String searchField,String searchString,String searchOper,Boolean _search){
        return slideshowService.findByPage(page,rows,searchField,searchString,searchOper,_search);
    }

    @RequestMapping("edit")
    @ResponseBody
    public Map edit(String oper ,Slideshow slideshow,String [] id) throws IOException  {
        if(oper.equals("add")){
            Map add = slideshowService.add(slideshow);
            return add;
        }if(oper.equals("del")){
            Map delete = slideshowService.delete(id);
            return  delete;
        }else {
            Slideshow ss = new Slideshow();
            ss.setId(slideshow.getId());
            Slideshow one = slideshowDao.selectOne(ss);
            if(slideshow.getPath().equals("")||slideshow.getPath().equals(null)){
                slideshow.setPath(one.getPath());
            }
            Map update = slideshowService.update(slideshow);
            return  update;
        }
    }
    @RequestMapping("upload")
    @ResponseBody
    public void upload(String slideshowId, MultipartFile path, HttpSession session, HttpServletRequest request) throws IOException {
        String realyPath=session.getServletContext().getRealPath("/upload");
        File file=new File(realyPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String newName=path.getOriginalFilename();
        newName=new Date().getTime()+"_"+newName;
        path.transferTo(new File(realyPath,newName));
        //网上路径
        String http = request.getScheme();
        String localHost = InetAddress.getLocalHost().toString();
        int port = request.getServerPort();
        String contextPath=request.getContextPath();
        String url = http+"://"+localHost.split("/")[1]+":"+port+contextPath+"/upload/"+newName;
        Slideshow slideshow=new Slideshow();
        slideshow.setPath(url);
        slideshow.setId(slideshowId);
        slideshowService.updateUrl(slideshow);
    }

    @RequestMapping("updataLoad")
    @ResponseBody
    public void updateUpload(String slideshowId, MultipartFile path, HttpSession session, HttpServletRequest request) throws IOException {
        if(path.getOriginalFilename().equals("") || path.getOriginalFilename().equals(null)) {
            System.out.println("嘿嘿,你不经过我");
        }else {
            String realyPath=session.getServletContext().getRealPath("/upload");
            File file=new File(realyPath);
            if(!file.exists()){
                file.mkdirs();
            }
            System.out.println("哈哈,你经过我");
            String newName=path.getOriginalFilename();
            newName=new Date().getTime()+"_"+newName;
            path.transferTo(new File(realyPath,newName));
            //网上路径
            String http = request.getScheme();
            String localHost = InetAddress.getLocalHost().toString();
            int port = request.getServerPort();
            String contextPath=request.getContextPath();
            String url = http+"://"+localHost.split("/")[1]+":"+port+contextPath+"/upload/"+newName;
            Slideshow slideshow=new Slideshow();
            slideshow.setPath(url);
            slideshow.setId(slideshowId);
            slideshowService.updateUrl(slideshow);
        }

    }

    //////////////////////////   专辑  ////////////////////////////////////
    @RequestMapping("AllSpecial")
    @ResponseBody
    public Map<String,Object> findByPageSpecial(Integer page,Integer rows,String searchField,String searchString,String searchOper,Boolean _search){
        return specialService.findByPage(page,rows,searchField,searchString,searchOper,_search);
    }

    @RequestMapping("editSpecial")
    @ResponseBody
    public Map editSpecial(String oper , Special special) throws IOException  {
        if(oper.equals("add")){
            Map add = specialService.add(special);
            return add;
        }if(oper.equals("del")){
            Map delete = specialService.delete(special.getId());
            return  delete;
        }else {
            Special special1 = new Special();
            special1.setId(special.getId());
            Special one = specialDao.selectOne(special1);
            if(special.getCover().equals("") || special.getCover().equals(null)){
                special.setCover(one.getCover());
            }
            Map update = specialService.update(special);
            return  update;
        }
    }

    @ResponseBody
    @RequestMapping("uploadSpecial")
    public void uploadSpecial(String specialId, MultipartFile cover, HttpSession session, HttpServletRequest request) throws IOException {
        String realyPath=session.getServletContext().getRealPath("/upload/special");
        File file=new File(realyPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String newName=cover.getOriginalFilename();
        newName=new Date().getTime()+"_"+newName;
        cover.transferTo(new File(realyPath,newName));
        //网上路径
        String http = request.getScheme();
        String localHost = InetAddress.getLocalHost().toString();
        int port = request.getServerPort();
        String contextPath=request.getContextPath();
        String url = http+"://"+localHost.split("/")[1]+":"+port+contextPath+"/upload/special/"+newName;
        Special special = new Special();
        special.setCover(url);
        special.setId(specialId);
        specialService.updateUrl(special);
    }

    @ResponseBody
    @RequestMapping("updateSpe")
    public void uploadSpe(String specialId, MultipartFile cover, HttpSession session, HttpServletRequest request) throws IOException {
        if(cover.getOriginalFilename().equals("")|| cover.getOriginalFilename().equals(null)){

        }else {
            String realyPath=session.getServletContext().getRealPath("/upload/special");
            File file=new File(realyPath);
            if(!file.exists()){
                file.mkdirs();
            }
            String newName=cover.getOriginalFilename();
            newName=new Date().getTime()+"_"+newName;
            cover.transferTo(new File(realyPath,newName));
            //网上路径
            String http = request.getScheme();
            String localHost = InetAddress.getLocalHost().toString();
            int port = request.getServerPort();
            String contextPath=request.getContextPath();
            String url = http+"://"+localHost.split("/")[1]+":"+port+contextPath+"/upload/special/"+newName;
            Special special = new Special();
            special.setCover(url);
            special.setId(specialId);
            specialService.updateUrl(special);
        }
    }

    ////////////////////////////章节//////////////////////////////////

    @RequestMapping("download")
    @ResponseBody
    public void download(String href, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = request.getSession().getServletContext().getRealPath("/mp3");
        String [] ss=href.split("/");
        String aa=ss[ss.length-1];
        File file=  new File(path,aa);
        FileInputStream is = new FileInputStream(file);
        String openStyle = "attachment";
        response.setHeader("content-disposition",openStyle+";fileName"+ URLEncoder.encode(aa,"UTF-8"));
        ServletOutputStream os=response.getOutputStream();
        IOUtils.copy(is,os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
    }

    @ResponseBody
    @RequestMapping("AllChapter")
    public Map<String,Object> findByPageChapter(Integer page,Integer rows,String Sid){
        return chapterService.findByPage(page,rows,Sid);
    }

    @RequestMapping("editChapter")
    @ResponseBody
    public Map editChapter(String oper , Chapter chapter,String Sid) throws IOException  {
        Special special=new Special();
        special.setId(Sid);
        Special spe= specialDao.selectOne(special);
        Integer number=spe.getNumber();
        if(oper.equals("add")){
            chapter.setSpecialId(Sid);
            Map add = chapterService.add(chapter);
            special.setNumber(number+1);
            specialService.update(special);
            return add;
        }if(oper.equals("del")){
            Map delete = chapterService.delete(chapter.getId());
            special.setNumber(number-1);
            specialService.update(special);
            return  delete;
        }else {
            Chapter cha = new Chapter();
            cha.setId(chapter.getId());
            chapterDao.selectOne(cha);
            if(chapter.getHref().equals("")){
                chapter.setHref(cha.getHref());
            }
            Map update = chapterService.update(chapter);
            return  update;
        }
    }

    @RequestMapping("addChapter")
    @ResponseBody
    public void uploaCheapter(String chapterId, MultipartFile href, HttpSession session, HttpServletRequest request) throws Exception {
        //获取绝对路径
        String realyPath1=session.getServletContext().getRealPath("/mp3");
        File file=new File(realyPath1);
        if(!file.exists()){
            file.mkdirs();
        }
        String newName=href.getOriginalFilename();
        newName="_"+newName;
        href.transferTo(new File(realyPath1,newName));
        //网上路径
        String http = request.getScheme();
        String localHost = InetAddress.getLocalHost().toString();
        int port = request.getServerPort();
        String contextPath=request.getContextPath();
        String url = http+"://"+localHost.split("/")[1]+":"+port+contextPath+"/mp3/"+newName;
        Chapter chapter = new Chapter();
        chapter.setHref(url);
        chapter.setId(chapterId);
        chapterService.updateUrl(chapter);
        //文件上传
        String realyPath=session.getServletContext().getRealPath("/mp3");
        File file1 = new File(realyPath,newName);
        long length=file1.length();
        String size=length/1024/1024+"MB";
        MP3File read = (MP3File) AudioFileIO.read(file1);
        MP3AudioHeader mp3AudioHeader = read.getMP3AudioHeader();
        // 获取当前音频有多少秒
        long trackLength = mp3AudioHeader.getTrackLength();
        String min = trackLength/60 +"分";
        String sed = trackLength%60 +"秒";
        chapter.setSizez(size);
        chapter.setDuration(min+sed);
        chapterDao.updateByPrimaryKeySelective(chapter);
    }

    @ResponseBody
    @RequestMapping("updateChe")
    public void uploaChe(String chapterId, MultipartFile href, HttpSession session, HttpServletRequest request) throws Exception {
        if(href.getOriginalFilename().equals("")|| href.getOriginalFilename().equals(null)){
            System.out.println("你没有更新图片");
        }else {
            //获取绝对路径
            String realyPath1=session.getServletContext().getRealPath("/mp3");
            File file=new File(realyPath1);
            if(!file.exists()){
                file.mkdirs();
            }
            String newName=href.getOriginalFilename();
            newName="_"+newName;
            href.transferTo(new File(realyPath1,newName));
            //网上路径
            String http = request.getScheme();
            String localHost = InetAddress.getLocalHost().toString();
            int port = request.getServerPort();
            String contextPath=request.getContextPath();
            String url = http+"://"+localHost.split("/")[1]+":"+port+contextPath+"/mp3/"+newName;
            Chapter chapter = new Chapter();
            chapter.setHref(url);
            chapter.setId(chapterId);
            chapterService.updateUrl(chapter);
            //文件上传
            String realyPath=session.getServletContext().getRealPath("/mp3");
            File file1 = new File(realyPath,newName);
            long length=file1.length();
            String size=length/1024/1024+"MB";
            MP3File read = (MP3File) AudioFileIO.read(file1);
            MP3AudioHeader mp3AudioHeader = read.getMP3AudioHeader();
            // 获取当前音频有多少秒
            long trackLength = mp3AudioHeader.getTrackLength();
            String min = trackLength/60 +"分";
            String sed = trackLength%60 +"秒";
            chapter.setSizez(size);
            chapter.setDuration(min+sed);
            chapterDao.updateByPrimaryKeySelective(chapter);
        }

    }

    ///////////////////////////////上师管理//////////////////////////////////////////////
    @RequestMapping("AllGuru")
    @ResponseBody
    public Map<String,Object> findByPageShangshi(Integer page,Integer rows){
        return guruService.searchAll(page,rows);
    }

    @ResponseBody
    @RequestMapping("SelectShangshi")
    public Guru selectOne(String id){
        Guru guru = new Guru();
        guru.setId(id);
        return guruDao.selectOne(guru);
    }

    ////////////////////////////////////////////////////
    @RequestMapping("upShangshi")
    @ResponseBody
    public void update(Guru guru,HttpSession session,HttpServletRequest request,MultipartFile upheadpic) throws IOException {
        //添加
        if(guru.getId().equals("")){
            guru.setId(UUID.randomUUID().toString());
            String realyPath=session.getServletContext().getRealPath("/shangshi");
            File file=new File(realyPath);
            if(!file.exists()){
                file.mkdirs();
            }
            String [] aa=guru.getHeadpic().split("\\\\");
            String newName=aa[aa.length-1];
            newName=new Date().getTime()+"_"+newName;
            upheadpic.transferTo(new File(realyPath,newName));
            //网上路径
            String http = request.getScheme();
            String localHost = InetAddress.getLocalHost().toString();
            int port = request.getServerPort();
            String contextPath=request.getContextPath();
            String url = http+"://"+localHost.split("/")[1]+":"+port+contextPath+"/shangshi/"+newName;
            guru.setHeadpic(url);
            guruDao.insert(guru);
        }
        //更新
        if(guru.getHeadpic().equals("") ||upheadpic.getOriginalFilename().equals("")){
            guru.setHeadpic(guruDao.selectOne(new Guru(guru.getId(),null,null,null)).getHeadpic());
            guruDao.updateByPrimaryKeySelective(guru);
        }else {
            String realyPath=session.getServletContext().getRealPath("/shangshi");
            File file=new File(realyPath);
            if(!file.exists()){
                file.mkdirs();
            }
            String [] aa=guru.getHeadpic().split("\\\\");
            String newName=aa[aa.length-1];
            newName=new Date().getTime()+"_"+newName;
            upheadpic.transferTo(new File(realyPath,newName));
            //网上路径
            String http = request.getScheme();
            String localHost = InetAddress.getLocalHost().toString();
            int port = request.getServerPort();
            String contextPath=request.getContextPath();
            String url = http+"://"+localHost.split("/")[1]+":"+port+contextPath+"/shangshi/"+newName;
            guru.setHeadpic(url);
            guruDao.updateByPrimaryKeySelective(guru);
        }
    }

    @ResponseBody
    @RequestMapping("deleteShangshi")
    public void delete(String id){
      guruService.delete(id);
    }

    /////////////////////////////////文章专辑////////////////////////////////////////////////////

    @RequestMapping("AllArticle")
    @ResponseBody
    public Map<String,Object> findByPageArticle(Integer page,Integer rows){
        return articleService.searchAll(page,rows);
    }

    @ResponseBody
    @RequestMapping("allsh")
    public List<Guru> allGurus(){
        List<Guru> gurus = guruDao.selectAll();
        return gurus;
    }

    @LogUtils("添加文章")
    @RequestMapping("addAtticle")
    @ResponseBody
    public void addArticle(Article article,HttpSession session,HttpServletRequest request,MultipartFile coverPic) throws IOException {
        if(article.getId().equals("")) {
            String realyPath=session.getServletContext().getRealPath("/upload/article");
            File file=new File(realyPath);
            if(!file.exists()){
                file.mkdirs();
            }
            String newName=coverPic.getOriginalFilename();
            newName=new Date().getTime()+"_"+newName;
            coverPic.transferTo(new File(realyPath,newName));
            //网上路径
            String http = request.getScheme();
            String localHost = InetAddress.getLocalHost().toString();
            int port = request.getServerPort();
            String contextPath=request.getContextPath();
            String url = http+"://"+localHost.split("/")[1]+":"+port+contextPath+"/upload/article/"+newName;
            article.setCover(url);
            article.setId(UUID.randomUUID().toString());
            articleDao.insert(article);
        }else {
            if(article.getCover().equals("")){
                Article article1 = new Article();
                article1.setId(article.getId());
                article.setCover(articleDao.selectOne(article1).getCover());
            }else {
                String realyPath=session.getServletContext().getRealPath("/upload/article");
                File file=new File(realyPath);
                if(!file.exists()){
                    file.mkdirs();
                }
                String newName=coverPic.getOriginalFilename();
                newName=new Date().getTime()+"_"+newName;
                coverPic.transferTo(new File(realyPath,newName));
                //网上路径
                String http = request.getScheme();
                String localHost = InetAddress.getLocalHost().toString();
                int port = request.getServerPort();
                String contextPath=request.getContextPath();
                String url = http+"://"+localHost.split("/")[1]+":"+port+contextPath+"/upload/article/"+newName;
                article.setCover(url);
            }
            articleDao.updateByPrimaryKeySelective(article);
        }
    }

    //文章封面上传，富文本编辑器
    @RequestMapping("uploadImg")
    @ResponseBody
    public Map uploadImg(MultipartFile imgFile,HttpSession session,HttpServletRequest request){
        HashMap hashMap = new HashMap();
        String dir = "/upload/contentImg/";
        try {
            String httpUrl = HttpUtil.getHttpUrl(imgFile, request, session, dir);
            hashMap.put("error",0);
            hashMap.put("url",httpUrl);
        } catch (Exception e) {
            hashMap.put("error",1);
            hashMap.put("message","上传错误");
            e.printStackTrace();
        }
        return hashMap;
    }

    //文章显示所有图片
    @RequestMapping("showAllImgs")
    @ResponseBody
    public Map showAllImgs(HttpSession session,HttpServletRequest request){
        // 1. 获取文件夹绝对路径
        String realPath = session.getServletContext().getRealPath("/upload/contentImg/");
        // 2. 准备返回的Json数据
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        // 3. 获取目标文件夹
        File file = new File(realPath);
        File[] files = file.listFiles();
        // 4. 遍历文件夹中的文件
        for (File file1 : files) {
            // 5. 文件属性封装
            HashMap fileMap = new HashMap();
            fileMap.put("is_dir",false);
            fileMap.put("has_file",false);
            fileMap.put("filesize",file1.length());
            fileMap.put("is_photo",true);
            // 获取文件后缀 | 文件类型
            String extension = FilenameUtils.getExtension(file1.getName());
            fileMap.put("filetype",extension);
            fileMap.put("filename",file1.getName());
            // 获取文件上传时间 1. 截取时间戳 2. 创建格式转化对象 3. 格式类型转换
            String s = file1.getName().split("_")[0];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(s)));
            fileMap.put("datetime",format);
            arrayList.add(fileMap);
        }
        hashMap.put("file_list",arrayList);
        hashMap.put("total_count",arrayList.size());
        // 返回路径为 项目名 + 文件夹路径
        hashMap.put("current_url",request.getContextPath()+"/upload/contentImg/");
        return hashMap;
    }

    @ResponseBody
    @RequestMapping("SelectArticle")
    public Article selectOneArticle(String id){
        Article article = new Article();
        article.setId(id);
        return  articleDao.selectOne(article);
    }

    //////////////////////////////////用户//////////////////////////////////////////////
    @ResponseBody
    @RequestMapping("userAll")
    public Map<String,Object> findByPageUser(Integer page,Integer rows) {
        return userService.searchAll(page,rows);
    }


    @LogUtils("修改用户")
    @RequestMapping("upUser")
    @ResponseBody
    public void upuser(String id){
        User user = new User();
        user.setId(id);
        User one = userDao.selectOne(user);
        if(one.getStatus().equals("正常")){
            user.setStatus("冻结");
            userDao.updateByPrimaryKeySelective(user);
        }else {
            user.setStatus("正常");
            userDao.updateByPrimaryKeySelective(user);
        }
    }

    ///////////////////导出轮播图信息////////////////////////////////
    @ResponseBody
    @RequestMapping("daochu")
    public void banner(){
        //controllerTask.run();
        try {
            slideshowService.daochu();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
