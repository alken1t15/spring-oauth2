package ru.javabegin.oauth2.spring.test_oauth2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class Test {

    @GetMapping("/delete")
    @PreAuthorize("hasRole('admin')")
    public String work(){
        return "delete work";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('admin')")
    public String internal(){
        return "add work";
    }
//    @PreAuthorize("hasRole('admin')")
//    и
//    @PreAuthorize("hasAuthority('ROLE_admin')")
    @GetMapping("/view")
    @PreAuthorize("hasRole('user')")
    public String view(){
       return  "view work";
    }
}
