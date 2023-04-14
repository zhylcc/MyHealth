package org.example.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.example.dao.CheckGroupDao;
import org.example.pojo.CheckGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService{
    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {
        checkGroupDao.add(checkGroup); //此时传入的对象尚未获得自增id
        Integer checkGroupId = checkGroup.getId(); //此处自增id需配合mapper映射文件在selectKey,在插入返回后取出
        this.setCheckGroupAndCheckItem(checkGroupId, checkItemIds);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkItemIds) {
        Integer id = checkGroup.getId();
        checkGroupDao.deleteAssociation(id); //先删除检查组与原检查项的关联
        this.setCheckGroupAndCheckItem(id, checkItemIds); //再设置检查组与所选检查项的关联
        checkGroupDao.edit(checkGroup); //更新检查组基本信息
    }

    private void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkItemIds) {
        if (checkItemIds != null && checkItemIds.length > 0) {
            for (Integer checkItemId: checkItemIds) {
                HashMap<String, Integer> map = new HashMap<>();
                map.put("checkGroupId", checkGroupId);
                map.put("checkItemId", checkItemId); //key名称与mapper映射文件sql语句占位符名称一致
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }
}
