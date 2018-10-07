package com.teacherselection.teacherselectionservice.controllers;

import com.teacherselection.teacherselectionservice.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class LoginController{
    @Autowired
    UserService userService;
    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;

    @RequestMapping(value="/api/login", method=RequestMethod.GET)
    public SecurityContext login(@RequestParam("email") String email,@RequestParam("psw") String psw) {
        try {
            Authentication auth = new UsernamePasswordAuthenticationToken(email,psw);
            Authentication authenticated = daoAuthenticationProvider.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(authenticated);
            return SecurityContextHolder.getContext();
        } catch (Exception e) {
            return SecurityContextHolder.getContext();
        }
    }
}