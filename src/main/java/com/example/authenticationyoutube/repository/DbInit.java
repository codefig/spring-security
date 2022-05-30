package com.example.authenticationyoutube.repository;

import com.example.authenticationyoutube.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {

    private UserRepository userRepository;

    DbInit(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        this.userRepository.deleteAll();
        User user = new User("user", encoder.encode("password"), "USER", "");
        User admin = new User("admin", encoder.encode("password"), "ADMIN", "LEVEL1,LEVEL2");
        List<User> usersList = Arrays.asList(user, admin);

        this.userRepository.saveAll(usersList);

    }
}
