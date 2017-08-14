package com.jason.menu.service.impl;

import com.jason.menu.dao.BaseDao;
import com.jason.menu.service.BaseService;

import java.io.Serializable;

/**
 * Created by Jason on 2017/8/14.
 */
public class  AbstractService<T, ID extends Serializable> implements BaseService<T, ID> {

    private BaseDao<T, ID> baseDao;

    @Override
    public void setBaseDao() {
        this.baseDao = baseDao;
    }

    @Override
    public int deleteByPrimaryKey(ID id) {
        return baseDao.deleteByPrimaryKey(id);
    }
    @Override
    public int insertSelective(T record) {
        return baseDao.insertSelective(record);
    }
    @Override
    public T selectByPrimaryKey(ID id) {
        return baseDao.selectByPrimaryKey(id);
    }
    @Override
    public int updateByPrimaryKeySelective(T record) {
        return baseDao.updateByPrimaryKey(record);
    }
    @Override
    public int updateByPrimaryKeyWithBLOBs(T record) {
        return baseDao.updateByPrimaryKeyWithBLOBs(record);
    }
    @Override
    public int updateByPrimaryKey(T record) {
        return baseDao.updateByPrimaryKey(record);
    }
    @Override
    public int insert(T record) {
        return baseDao.insert(record);
    }
}
