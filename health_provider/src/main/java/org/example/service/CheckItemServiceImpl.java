package org.example.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.dao.CheckItemDao;
import org.example.entity.PageResult;
import org.example.entity.QueryPageBean;
import org.example.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService{
    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public CheckItem find(Integer id) {
        return checkItemDao.findById(id);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckItem> rows = page.getResult();
        return new PageResult(total, rows);
    }

    @Override
    public void delete(Integer id) {
        if (checkItemDao.findCountByCheckItemId(id) > 0) {
            throw new RuntimeException("Cannot delete or update a parent row: a foreign key constraint fails.");
        }
        checkItemDao.deleteById(id);
    }

    @Override
    public void edit(CheckItem checkItem) {
        if (checkItemDao.findById(checkItem.getId()) != null) {
            checkItemDao.edit(checkItem);
            int i=1/0;
        }
    }

}
