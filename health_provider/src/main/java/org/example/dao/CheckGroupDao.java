package org.example.dao;

import org.example.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    public void add(CheckGroup checkGroup);
    public void setCheckGroupAndCheckItem(Map map);

    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    public void edit(CheckGroup checkGroup);
    public void deleteAssociation(Integer id);
}
