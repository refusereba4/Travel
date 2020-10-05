package com.ali.dao;

import com.ali.domain.User;

public interface UserDao {

   /*
    * 根据用户名查询用户信息
    * @param username
    * @return
    */

   public User findByUsername(String username);
   /*
    * 用户保存
    * @param user
    */
   public void save(User user);

   /*
      根据激活码查询用户
    */

   User findByCode(String code);

   /*
      更新用户状态
    */

   void updateStatus(User user);

   /*
      登录查询
    */
   User findByUsernameAndPassword(String username,String password);


}
