package com.ali.service.impl;

import com.ali.dao.CategoryDao;
import com.ali.dao.impl.CategoryDaoImpl;
import com.ali.domain.Category;
import com.ali.service.CategoryService;
import com.ali.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao=
            new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {

        //1.从redid查询
        //1.1 获取redis客户端
        Jedis jedis= JedisUtil.getJedis();
        //1.2 可使用sortedset排序查询
        Set<Tuple> categorys=
        jedis.zrangeWithScores("category",0,-1);

        //2.判断查询的集合是否为空
        List<Category> cs=null;

        if(categorys==null||categorys.size()==0){
            //3.如果为空，需要从数据库查询，再将数据存入redis
            System.out.println("从数据库查询");
            //3.1 Dao
            cs=categoryDao.findAll();
            //3.2 将集合数据存储到redis中【category，】
            for(int i=0;i<cs.size();i++){
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else{
            //4.如果非空，代表redis有先前数据，直接从redis中果
            System.out.println("从Redis查询");
            //将Set的数据存入list
            cs=new ArrayList<Category>();
            for(Tuple tuple:categorys){
              Category category=new Category();
              category.setCname(tuple.getElement());
              category.setCid((int)tuple.getScore());
              cs.add(category);
            }
        }

        return cs;
    }
}
