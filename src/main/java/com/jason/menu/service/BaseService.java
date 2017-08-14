package com.jason.menu.service;

import java.io.Serializable;

/**
 * Created by Jason on 2017/8/14.
 */
public interface BaseService <T,ID extends Serializable> {
        void setBaseDao();
        int deleteByPrimaryKey(ID id);
        int insert(T record);
        int insertSelective(T record);
        T selectByPrimaryKey(ID id);
        int updateByPrimaryKeySelective(T record);
        int updateByPrimaryKeyWithBLOBs(T record);
        int updateByPrimaryKey(T record);
    }
