package com.example.authenticationyoutube.controllers;

import com.example.authenticationyoutube.model.User;
import com.example.authenticationyoutube.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/index")
    public ResponseEntity<?> index(){
        return ResponseEntity.ok("index homepage");
    }

    @GetMapping("/welcome")
    public ResponseEntity<?> welcome(){
        return ResponseEntity.ok("This is the welcome page");
    }

    @GetMapping("/admin")
    public ResponseEntity<?> admin(){
        return ResponseEntity.ok("This is for admin alone");
    }

    @GetMapping("/user")
    public ResponseEntity<?> user(){
        return ResponseEntity.ok("This is for user alone");
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard(){
        return ResponseEntity.ok("Welcome to the dashboard page");
    }

    @GetMapping("/moshood")
    public ResponseEntity<?>  users(){
        return new ResponseEntity(this.userRepository.findAll(), HttpStatus.OK);
    }
}
