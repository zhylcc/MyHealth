package org.example.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.example.constant.RedisConstant;
import org.example.dao.SetmealDao;
import org.example.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService{
    @Autowired
    private SetmealDao setmealDao;

    @Override
    public void add(Setmeal setmeal, Integer[] checkGroupIds) {
        setmealDao.add(setmeal);
        Integer id = setmeal.getId();
        this.setSetmealAndCheckGroup(id, checkGroupIds);
    }

    @Override
    public Setmeal findDetailById(Integer id) {
        return setmealDao.findDetailById(id);
    }

    private void setSetmealAndCheckGroup(Integer setmealId, Integer[] checkGroupIds) {
        if (checkGroupIds != null && checkGroupIds.length > 0) {
            for (Integer checkGroupId: checkGroupIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("setmealId", setmealId);
                map.put("checkGroupId", checkGroupId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }
    }
}
