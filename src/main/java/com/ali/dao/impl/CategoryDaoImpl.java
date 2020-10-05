package com.ali.dao.impl;

import com.ali.dao.CategoryDao;
import com.ali.domain.Category;
import com.ali.util.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    private JdbcTemplate template=new JdbcTemplate(JdbcUtils.getDataSource());

    @Override
    public List<Category> findAll() {
        String sql="select * from tab_category";

        return template.query(sql,new BeanPropertyRowMapper<Category>(Category.class));
    }
}
