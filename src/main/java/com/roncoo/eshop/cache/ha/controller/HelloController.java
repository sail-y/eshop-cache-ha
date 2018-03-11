package com.roncoo.eshop.cache.ha.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangfan
 * @date 2018/03/11
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(String name) {
        return "hello, " + name;
    }
}
