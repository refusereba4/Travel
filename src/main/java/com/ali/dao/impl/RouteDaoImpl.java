package com.ali.dao.impl;

import com.ali.dao.RouteDao;
import com.ali.domain.Route;
import com.ali.util.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate template=
            new JdbcTemplate(JdbcUtils.getDataSource());

    @Override
    public int findTotalCount(int cid,String rname) {
        String sql="select count(*) from tab_route where 1=1";
        StringBuffer sb=new StringBuffer(sql);

        List params=new ArrayList(); //多个条件

        //2.判断参数是否有值
        if(cid!=0){
            sb.append(" and cid=?");
            params.add(cid);//添加？对象的值
        }

        if(rname!=null&&rname.length()>0){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }

        sql=sb.toString();


        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String rname) {
        String sql="select * from tab_route where 1 = 1";
        StringBuffer sb=new StringBuffer(sql);
        List params=new ArrayList();

        if(cid!=0){
            sb.append(" and cid= ?");
            params.add(cid);
        }

        if(rname!=null&&rname.length()>0){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }

        sb.append(" limit ?,?");
        sql=sb.toString();

        params.add(start);
        params.add(pageSize);


        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class)
                    ,params.toArray());
    }

    @Override
    public Route findOne(int rid){
        String sql="select * from tab_route where rid = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
}
