package org.example.service;

import org.example.entity.PageResult;
import org.example.entity.QueryPageBean;
import org.example.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    public void add(CheckItem checkItem);

    public CheckItem find(Integer id);

    public List<CheckItem> findAll();

    public PageResult findPage(QueryPageBean queryPageBean);

    public void delete(Integer id);

    public void edit(CheckItem checkItem);
}
