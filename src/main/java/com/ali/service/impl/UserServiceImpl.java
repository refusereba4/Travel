package com.ali.service.impl;

import com.ali.dao.UserDao;
import com.ali.dao.impl.UserDaoImpl;
import com.ali.domain.User;
import com.ali.service.UserService;
import com.ali.util.MailUtils;
import com.ali.util.UuidUtil;

public class UserServiceImpl implements UserService {

    private UserDao userDao=new UserDaoImpl();
    @Override
    public boolean regist(User user) {
       User u= userDao.findByUsername(user.getUsername());
       //判断是否为null
        if(u!=null){
            //用户名存在，注册失败
            return false;
        }
        //2.1设置激活码 唯一字符串
        user.setCode(UuidUtil.getUuid());
        //2.2 设置源活状态
        user.setStatus("N");
        userDao.save(user);

        //3.激活邮件发送
        String content="<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活[阿里旅游网]</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }


    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user=userDao.findByCode(code);
        if(user!=null){
            userDao.updateStatus(user);
            return true;
        }else{
            return false;
        }


    }


    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(),
                                                 user.getPassword());
    }
}
