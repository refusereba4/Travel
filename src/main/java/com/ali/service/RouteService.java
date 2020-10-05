package com.ali.service;

import com.ali.domain.PageBean;
import com.ali.domain.Route;

public interface RouteService {

    public PageBean pageQuery(int cid,int currentPage,int pageSize,String rname);
    public Route findOne(String rid);
}
