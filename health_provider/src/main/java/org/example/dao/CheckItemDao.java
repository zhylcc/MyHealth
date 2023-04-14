package org.example.dao;

import com.github.pagehelper.Page;
import org.example.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
    public void add(CheckItem checkItem);

    public CheckItem findById(Integer id);

    public List<CheckItem> findAll();

    public Page<CheckItem> selectByCondition(String queryString);

    public void deleteById(Integer id);
    public long findCountByCheckItemId(Integer checkItemId);

    public void edit(CheckItem checkItem);
}
