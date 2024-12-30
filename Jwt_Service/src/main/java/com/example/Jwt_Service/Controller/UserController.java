package com.example.Jwt_Service.Controller;

import com.example.Jwt_Service.Entity.UserProfile;
import com.example.Jwt_Service.Service.JwtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtUserService jwtUserService;

    @PostMapping("/register")
    public ResponseEntity<UserProfile> create(@RequestBody UserProfile userProfile){
        return new ResponseEntity<>(jwtUserService.create(userProfile), HttpStatus.CREATED);
    }

    @GetMapping("/allusers")
    public ResponseEntity<List<UserProfile>>getusers(){
        return new ResponseEntity<>(jwtUserService.getallusers(),HttpStatus.FOUND);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserProfile userProfile){
        return jwtUserService.verify(userProfile);
    }
}
