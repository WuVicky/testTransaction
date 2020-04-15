package com.transaction.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/test")
public class TestController {
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Object register(HttpServletRequest request, HttpServletResponse response) {
        return "aaa";
    }



}
