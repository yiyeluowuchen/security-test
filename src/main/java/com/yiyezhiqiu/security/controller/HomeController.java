package com.yiyezhiqiu.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//这里是controller，如果要返回前端控制
@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("msg", "Hi~欢迎");
        return "home";
    }
    @RequestMapping("/login")
    public  String login(){
        return "login";
    }
    @RequestMapping("/admin")
    @ResponseBody// 这个是为了返回一个字符串
    public String hello(){
        return "hello admin";
    }

    @GetMapping("/aa")
    public String aa(){
        return "aa";
    }


}