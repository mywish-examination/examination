package com.home.examination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/testController")
public class TestController {

    @RequestMapping("/health")
    public ModelAndView health() {
        ModelAndView modelAndView = new ModelAndView("test");
        return modelAndView;
    }
}
