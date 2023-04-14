package org.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.example.constant.MessageConstant;
import org.example.constant.RedisMessageConstant;
import org.example.entity.Result;
import org.example.pojo.Member;
import org.example.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Reference
    private MemberService memberService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/login")
    public Result login(HttpServletResponse response, @RequestBody Map map) {
        String telephone = (String) map.get("telephone");
        if (telephone == null || !Pattern.compile("^1[34578]\\d{9}$").matcher(telephone).matches()) {
            return new Result(false, MessageConstant.LOGIN_FAIL, "请输入正确的手机号");
        }
        String validateCode = (String) map.get("validateCode");
        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if (codeInRedis == null || !codeInRedis.equals(validateCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        try {
            Member member = memberService.findByTelephone(telephone);
            if (member == null) { //未注册会员自动完成注册
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);
            }
            //写入Cookie,跟踪登录会员
            Cookie cookie = new Cookie("login_member_telephone", telephone);
            cookie.setPath("/");
            cookie.setMaxAge(60*60*24*7);
            response.addCookie(cookie);
            //写入redis
            String json = JSON.toJSON(member).toString();
            jedisPool.getResource().setex(
                    telephone,
                    60*30,
                    json
            );
            return new Result(true, MessageConstant.LOGIN_SUCCESS, validateCode);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.LOGIN_FAIL);
        }
    }
}
