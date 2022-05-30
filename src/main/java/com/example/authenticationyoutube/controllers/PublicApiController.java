package com.example.authenticationyoutube.controllers;

import com.example.authenticationyoutube.repository.UserRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/public")
public class PublicApiController {
    private UserRepository userRepository;

    public PublicApiController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/test")
    public String test(){
        return "test enpdoint";
    }

    @GetMapping("/manager")
    public String manager(){
        return "manager enpdoint";
    }
}
