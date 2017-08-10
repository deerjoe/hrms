package com.jason.hrms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jason.hrms.dao.PositionDao;
import com.jason.hrms.domain.Position;
import com.jason.hrms.service.PositionService;

@Service("positionService")
public class PositionServiceImpl implements PositionService {

    // 自动注入 DAO 对象
    @Resource
    private PositionDao positionDao;

    // 实现接口中的方法    

    @Override
    public List<Position> findPositions(Map<String, Object> map) {

        return positionDao.findPositions(map);
    }

    @Override
    public Integer getCount(Map<String, Object> map) {

        return positionDao.getCount(map);
    }

    @Override
    public Integer addPosition(Position position) {

        return positionDao.addPosition(position);
    }

    @Override
    public Integer updatePosition(Position position) {

        return positionDao.updatePosition(position);
    }

    @Override
    public Integer deletePosition(Integer id) {
        Integer flag = null;
        // 如果删除关联外键的行记录，抛出异常
        try {
            flag = positionDao.deletePosition(id);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        return flag;
    }

}
