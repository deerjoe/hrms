package com.jason.menu.dao;

import com.jason.menu.domain.Menu;

import java.util.List;

public interface MenuDao extends  BaseDao<Menu,Integer>{
    List<Menu> selectAll();
}