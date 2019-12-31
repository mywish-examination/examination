package com.home.examination.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.UserDO;
import com.home.examination.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/app/user")
public class UserAppController {

    @Resource
    private UserService userService;
    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping("/detail")
    public UserDO detail(Long id) {
        return userService.getById(id);
    }

    @PostMapping("/login")
    public UserDO login(@RequestParam String loginName, @RequestParam String password) {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getLoginName, loginName).eq(UserDO::getPassword, password).eq(UserDO::getType, "0");

        UserDO one = userService.getOne(queryWrapper);
        if (one != null) {
            String uuid = UUID.randomUUID().toString();

            redisTemplate.opsForValue().set(uuid, one, 30, TimeUnit.MINUTES);
            one.setToken(uuid);
        }

        return one;
    }

    @GetMapping("/logout")
    public Map<String, String> logout(@RequestParam String token) {
        Boolean delete = redisTemplate.delete(token);
        Map<String, String> map = new HashMap<>();
        if(delete) {

        }
        map.put("status", "success");
        return map;
    }

}