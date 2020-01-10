package com.home.examination.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.UserDO;
import com.home.examination.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    /**
     * 登录
     *
     * @param loginName
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam String loginName, @RequestParam String password) {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getLoginName, loginName).eq(UserDO::getPassword, password);
        Map<String, Object> map = new HashMap<>();

        UserDO one = userService.getOne(queryWrapper);
        if (one != null) {
            String uuid = UUID.randomUUID().toString();

            redisTemplate.opsForValue().set(uuid, one, 30, TimeUnit.MINUTES);
            one.setToken(uuid);

            map.put("status", "success");
        } else {
            map.put("status", "error");
        }
        map.put("user", one);

        return map;
    }

    /**
     * 退出
     *
     * @param token
     * @return
     */
    @GetMapping("/logout")
    public Map<String, String> logout(@RequestParam String token) {
        Boolean delete = redisTemplate.delete(token);
        Map<String, String> map = new HashMap<>();
        if (delete) {
            map.put("status", "success");
        } else {
            map.put("status", "error");
        }
        return map;
    }

    /**
     * 注册（手机号、验证码、序列号、授权码）
     *
     * @param userDO
     * @return
     */
    @PostMapping("/register")
    public Map<String, String> register(UserDO userDO) {
        Map<String, String> map = new HashMap<>();
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getLoginName, userDO.getLoginName());
        UserDO one = userService.getOne(queryWrapper);
        if (one != null) {
            map.put("status", "error");
            map.put("msg", "手机号不能重复");
            return map;
        }
        boolean save = userService.save(userDO);
        if (save) {
            map.put("status", "success");
        } else {
            map.put("status", "error");
        }
        return map;
    }

    /**
     * 完善信息
     *
     * @param userDO
     * @return
     */
    @PostMapping("/perfectInformation")
    public Map<String, String> perfectInformation(UserDO userDO) {
        Map<String, String> map = new HashMap<>();

        UserDO user = (UserDO) redisTemplate.opsForValue().get(userDO.getToken());
        if (user == null) {
            map.put("status", "error");
            map.put("msg", "该用户没有登录，不能设置密码");
            return map;
        }

        BeanUtils.copyProperties(userDO, user);

        boolean result = userService.saveOrUpdate(user);
        ;
        if (result) {
            map.put("status", "success");
        } else {
            map.put("status", "error");
        }
        return map;
    }

    /**
     * 设置登密码
     *
     * @param token
     * @param password
     * @return
     */
    @PostMapping("/settingLoginPassword")
    public Map<String, String> settingLoginPassword(@RequestParam String token, @RequestParam String password) {
        Map<String, String> map = new HashMap<>();

        UserDO userDO = (UserDO) redisTemplate.opsForValue().get(token);
        if (userDO == null) {
            map.put("status", "error");
            map.put("msg", "该用户没有登录，不能设置密码");
            return map;
        }

        userDO.setPassword(password);
        boolean result = userService.saveOrUpdate(userDO);
        ;
        if (result) {
            map.put("status", "success");
        } else {
            map.put("status", "error");
        }
        return map;
    }

}