package com.jason.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jason.dao.DepartmentDao;
import com.jason.domain.Department;
import com.jason.service.DepartmentService;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {

    // 自动注入 DAO 对象
    @Resource
    private DepartmentDao departmentDao;

    // 实现接口中的方法

    @Override
    public List<Department> findDepartments(Map<String, Object> map) {

        return departmentDao.findDepartments(map);
    }

    @Override
    public Integer getCount(Map<String, Object> map) {

        return departmentDao.getCount(map);
    }

    @Override
    public Integer addDepartment(Department department) {

        return departmentDao.addDepartment(department);
    }

    @Override
    public Integer updateDepartment(Department department) {

        return departmentDao.updateDepartment(department);
    }

    @Override
    public Integer deleteDepartment(Integer id) {
        Integer flag = null;
        // 如果删除关联外键的行记录，抛出异常
        try {
            flag =  departmentDao.deleteDepartment(id);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        return flag;
    }
}
