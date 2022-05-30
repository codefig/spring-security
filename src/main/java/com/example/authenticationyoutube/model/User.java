package com.example.authenticationyoutube.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    private String roles = "";

    private int active;

    private String permissions = "";

    public User(String username, String password, String roles, String permissions){
        this.active = 1;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.permissions = permissions;
    }

    public List<String> getRoleList(){
        if(roles.length() > 0){
            return Arrays.asList(roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList(){
        if(permissions.length() > 0){
            return Arrays.asList(permissions.split(","));
        }
        return new ArrayList<>();
    }

}
