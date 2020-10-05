package com.ali.dao;

import com.ali.domain.Favorite;

public interface FavoriteDao {

    public int findCountByRid(int rid);

    public Favorite findByRidAndUid(int rid, int uid);

    void add(int rid,int uid);
}
