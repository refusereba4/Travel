package com.ali.service;

import com.ali.domain.User;

public interface UserService {

    /*
       注册用户
     */

    boolean regist(User user);

    /*
       激活用户
     */

    boolean active(String code);

     /*
      用户登录
    */

    User login (User user);
}


