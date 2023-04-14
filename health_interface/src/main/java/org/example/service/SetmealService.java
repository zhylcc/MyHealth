package org.example.service;

import org.example.pojo.Setmeal;

public interface SetmealService {
    public void add(Setmeal setmeal, Integer[] checkGroupIds);

    public Setmeal findDetailById(Integer id);
}
