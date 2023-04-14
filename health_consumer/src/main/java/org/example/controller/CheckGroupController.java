package org.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.example.constant.MessageConstant;
import org.example.entity.Result;
import org.example.pojo.CheckGroup;
import org.example.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;

    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds) {
        if (checkGroup.getCode() == null || checkGroup.getName() == null) {
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL, "code和name为必填字段");
        }
        try {
            checkGroupService.add(checkGroup, checkItemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id) {
        try {
            List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEMLIST_SUCCESS, checkItemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEMLIST_FAIL);
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds) {
        if (checkGroup.getId() == null || checkGroup.getCode() == null || checkGroup.getName() == null) {
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL, "id,code和name为必填字段");
        }
        try {
            checkGroupService.edit(checkGroup, checkItemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
}
