package org.example.dao;

import org.example.pojo.Setmeal;

import java.util.Map;

public interface SetmealDao {
    public void add(Setmeal setmeal);
    public void setSetmealAndCheckGroup(Map map);

    public Setmeal findDetailById(Integer id);
}
