package com.ali.service.impl;

import com.ali.dao.*;
import com.ali.dao.impl.FavoritDaoImpl;
import com.ali.dao.impl.RouteDaoImpl;
import com.ali.dao.impl.RouteImageDaoImpl;
import com.ali.dao.impl.SellerDaoImpl;
import com.ali.domain.PageBean;
import com.ali.domain.Route;
import com.ali.domain.RouteImg;
import com.ali.domain.Seller;
import com.ali.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao=new RouteDaoImpl();
    private RouteImageDao routeImageDao=new RouteImageDaoImpl();
    private SellerDao sellerDao=new SellerDaoImpl();
    private FavoriteDao favoriteDao=new FavoritDaoImpl();

    @Override
    public PageBean pageQuery(int cid, int currentPage, int pageSize,String rname) {
        //封装PageBean
        PageBean<Route> pb=new PageBean<Route>();

        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);

        int totalCount=routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);

        int totalPage=totalCount%pageSize==0?
                totalCount/pageSize:(totalCount/pageSize)+1;
        pb.setTotalPages(totalPage);

        System.out.println(totalPage);

        int start=(currentPage-1)*pageSize;

        List<Route> list= routeDao.findByPage(cid,start,pageSize,rname);
        pb.setList(list);


        return pb;
    }

    @Override
    public Route findOne(String rid) {
        //1.根据id查route表中查询route对象
        Route route=routeDao.findOne(Integer.parseInt(rid));

        //2.根据route的id 查询图片集合信息
        List<RouteImg> routeImgList=routeImageDao.findByRid(route.getRid());
        //2.1 将集合设置到route对象
        route.setRouteImgList(routeImgList);
        //3. 根据route的sid(商家id)查询商家对象
        Seller seller=sellerDao.findById(route.getSid());
        route.setSeller(seller);
        //4.查询收藏的次数
        int count= favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);


        return route;
    }
}
