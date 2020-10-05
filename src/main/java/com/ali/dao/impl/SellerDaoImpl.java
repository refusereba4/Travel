package com.ali.dao.impl;

import com.ali.dao.SellerDao;
import com.ali.domain.Seller;
import com.ali.util.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {

    private JdbcTemplate jdbcTemplate=
            new JdbcTemplate(JdbcUtils.getDataSource());

    @Override
    public Seller findById(int id) {
        String sql="select * from tab_seller where sid = ?";

        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Seller.class),id);
    }
}
