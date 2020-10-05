package com.ali.dao.impl;

import com.ali.dao.RouteImageDao;
import com.ali.domain.RouteImg;
import com.ali.util.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImageDaoImpl implements RouteImageDao {

    private JdbcTemplate jdbcTemplate=
            new JdbcTemplate(JdbcUtils.getDataSource());

    @Override
    public List<RouteImg> findByRid(int rid) {
        String sql="select * from tab_route_img where rid = ?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);
    }
}
