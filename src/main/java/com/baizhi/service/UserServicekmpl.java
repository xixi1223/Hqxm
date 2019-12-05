package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.MapOV;
import com.baizhi.entity.User;
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
 * Created by XIXI on 2019/12/1.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserServicekmpl implements  UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public Map<String, Object> searchAll(Integer page, Integer rows) {
        Map<String,Object> map=new HashMap<>();
        List<User> list=null;
        Integer total=0;
        Integer records=0;

        int pa=(page-1)*rows;
        list = userDao.selectByRowBounds(new User(),new RowBounds(pa,rows));
        records=userDao.selectAll().size();

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
    public int count(String sex, Integer days) {
        return userDao.queryBySex(sex,days);
    }

    @Override
    public List<MapOV> findByAddress() {
        return userDao.queryAddress();
    }

    ///////////接口方法////////////////////////////
    @Override
    public User login(String phone, String password) {
        User user = new User();
        user.setPhone(phone).setPassword(password);
        return userDao.selectOne(user);
    }

    //注册
    @Override
    public String registUser(String phone) {
        User user = new User();
        String id=UUID.randomUUID().toString();
        user.setId(id).setPhone(phone);
        userDao.insert(user);
        return id;
    }
    //补充信息
    @Override
    public User regist(String id,User user) {
        user.setId(id);
        userDao.updateByPrimaryKeySelective(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        userDao.updateByPrimaryKeySelective(user);
        return  userDao.selectOne(new User().setId(user.getId()));
    }
}
