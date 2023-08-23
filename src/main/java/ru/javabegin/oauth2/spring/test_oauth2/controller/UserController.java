package ru.javabegin.oauth2.spring.test_oauth2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {
    @GetMapping("/view")
//    @PreAuthorize("hasRole('user')")
    public String view(){
        return  "view work";
    }
}
