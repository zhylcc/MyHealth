package org.example.controller;

import org.example.constant.MessageConstant;
import org.example.constant.RedisMessageConstant;
import org.example.entity.Result;
import org.example.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {
        if (telephone == null || !Pattern.compile("^1[34578]\\d{9}$").matcher(telephone).matches()) {
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL, "请输入正确的手机号");
        }
        try {
            Integer code = ValidateCodeUtils.generateValidateCode(4);
            jedisPool.getResource().setex(
                    telephone + RedisMessageConstant.SENDTYPE_ORDER,
                    5*60,
                    code.toString()
            );
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS, code);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone) {
        if (telephone == null || !Pattern.compile("^1[34578]\\d{9}$").matcher(telephone).matches()) {
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL, "请输入正确的手机号");
        }
        try {
            Integer code = ValidateCodeUtils.generateValidateCode(6);
            jedisPool.getResource().setex(
                    telephone+RedisMessageConstant.SENDTYPE_LOGIN,
                    5*60,
                    code.toString()
            );
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS, code);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
