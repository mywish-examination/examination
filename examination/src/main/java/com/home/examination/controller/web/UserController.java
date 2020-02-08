package com.home.examination.controller.web;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.UserDO;
import com.home.examination.entity.page.UserPager;
import com.home.examination.entity.vo.SuggestVO;
import com.home.examination.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/web/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/listPage")
    @ResponseBody
    public UserPager listPage(UserPager pager) {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        userService.page(pager.getPager(), queryWrapper);
        return pager;
    }

    @GetMapping("/listSuggest")
    @ResponseBody
    public SuggestVO<UserDO> listSuggest(UserDO userDO) {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.apply(!StringUtils.isEmpty(userDO.getTrueName()), " name like '%" + userDO.getTrueName() + "%'");
        List<UserDO> list = userService.list(queryWrapper);

        return new SuggestVO<>(list);
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = userService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        UserDO userDO = new UserDO();
        if (id != null) {
            userDO = userService.getById(id);
        }
        ModelAndView mav = new ModelAndView("/pages/user/modify");
        model.addAttribute("user", userDO);

        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(UserDO param) {
        ModelAndView mav = new ModelAndView("/pages/user/list");
        userService.saveOrUpdate(param);
        return mav;
    }

    @PostMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response,
                      @RequestParam String loginName, @RequestParam String password) throws IOException {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getLoginName, loginName).eq(UserDO::getPassword, password);
        UserDO one = userService.getOne(queryWrapper);
        if (one == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            String uuid = UUID.randomUUID().toString();
            Cookie token = new Cookie("token", uuid);
            token.setPath(request.getContextPath());
            token.setMaxAge(60 * 30);
            response.addCookie(token);

            Cookie trueName = new Cookie("trueName", one.getTrueName());
            trueName.setPath(request.getContextPath());
            trueName.setMaxAge(60 * 30);
            response.addCookie(trueName);

            Cookie loginUser = new Cookie("loginName", loginName);
            loginUser.setPath(request.getContextPath());
            loginUser.setMaxAge(60 * 30);
            response.addCookie(loginUser);

            redisTemplate.opsForValue().set(uuid, one, 30, TimeUnit.MINUTES);
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                cookie.setMaxAge(0);
                cookie.setPath(request.getContextPath());
                response.addCookie(cookie);
                redisTemplate.delete(cookie.getValue());
            }
        }
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }

}