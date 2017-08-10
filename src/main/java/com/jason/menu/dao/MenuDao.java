package com.jason.menu.dao;

import com.jason.menu.domain.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository public interface MenuDao {
    /** 查找菜单
     *
     * @param menu
     * @return
     */
    public Menu findMenu(Menu menu);
    /** 根据条件查询菜单
     *
     * @param menu
     * @return
     */
    public List<Menu> findMenus(Menu menu);

    /**
     * 统计菜单总数
     * @param map
     * @return
     */
    public Integer getCount(Map<String, Object> map);

    /**
     * 增加菜单
     * @param menu
     * @return
     */
    public Integer addMenu(Menu menu);

    /**
     * 更新菜单
     * @param menu
     * @return
     */
    public Integer updateMenu(Menu menu);

    /**
     * 根据id删除菜单
     * @param id
     * @return
     */
    public Integer deleteMenu(Integer id);
}
