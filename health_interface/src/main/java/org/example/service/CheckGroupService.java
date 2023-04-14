package org.example.service;

import org.example.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    public void add(CheckGroup checkGroup, Integer[] checkItemIds);

    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    public void edit(CheckGroup checkGroup, Integer[] checkItemIds);
}
