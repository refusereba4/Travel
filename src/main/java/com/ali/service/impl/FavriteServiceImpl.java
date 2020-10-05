package com.ali.service.impl;

import com.ali.dao.FavoriteDao;
import com.ali.dao.impl.FavoritDaoImpl;
import com.ali.domain.Favorite;
import com.ali.service.FavoriteService;

public class FavriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao=new FavoritDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite=
        favoriteDao.findByRidAndUid(Integer.parseInt(rid),uid);
        return favorite!=null;
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);

    }
}
