package org.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.example.constant.MessageConstant;
import org.example.entity.Result;
import org.example.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;

    @RequestMapping("/loginForward")
    public Result loginForward(@RequestParam("s") Integer status) {
        if (status == 0) {
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        }
        return new Result(false, MessageConstant.LOGIN_FAIL);
    }

//    @RequestMapping("/logoutForward")
//    public Result logoutForward() {
//        return new Result(true, MessageConstant.LOGOUT_SUCCESS);
//
//    }

    @RequestMapping("/getUsername")
    public Result getUsername() {
        User user = null;
        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
        return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, user.getUsername());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/getDetail")
    public Result getDetail() {
        User user = null;
        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERDETAIL_FAIL);
        }
        return new Result(true, MessageConstant.GET_USERDETAIL_SUCCESS, userService.findByUsername(user.getUsername()));
    }
}
