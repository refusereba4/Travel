package com.ali.dao;

import com.ali.domain.RouteImg;

import java.util.List;

public interface RouteImageDao {

    public List<RouteImg> findByRid(int rid);
}
