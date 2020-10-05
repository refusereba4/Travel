package com.ali.dao.impl;

import com.ali.dao.FavoriteDao;
import com.ali.domain.Favorite;
import com.ali.util.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoritDaoImpl implements FavoriteDao {

    private JdbcTemplate jdbcTemplate=
            new JdbcTemplate(JdbcUtils.getDataSource());

    @Override
    public int findCountByRid(int rid) {
        String sql="select count(*) from tab_favorite where rid = ?";
        return jdbcTemplate.queryForObject(sql,Integer.class,rid);
    }


    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        String sql="select * from tab_favorite where rid=? and uid=? ";
        Favorite favorite=
                jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Favorite>(Favorite.class)
                ,rid,uid);
        return favorite;
    }

    @Override
    public void add(int rid, int uid) {
     String sql="insert into tab_favorite values(?,?,?)";
     jdbcTemplate.update(sql,rid,new Date(),uid);
    }
}
