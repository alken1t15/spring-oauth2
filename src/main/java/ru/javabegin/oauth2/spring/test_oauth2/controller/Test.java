package ru.javabegin.oauth2.spring.test_oauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class Test {

    @GetMapping("/login")
    public String work(){
        return "login work";
    }

    @GetMapping("/internal")
    public String internal(){
        return "internal work";
    }
}
