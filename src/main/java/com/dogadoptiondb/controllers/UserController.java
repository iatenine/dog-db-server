package com.dogadoptiondb.controllers;

import com.dogadoptiondb.models.User;
import com.dogadoptiondb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController
{
    @Autowired
    UserService us;

    @PostMapping(value = "/user",consumes = "application/json",produces = "application/json")
    public ResponseEntity<User> newUser(@RequestParam(name = "name") String name, @RequestParam(name = "username") String username,@RequestParam(name = "email")
            String email,@RequestParam(name = "phone") String phone, @RequestParam(name = "password") String password)
    {
        if(!name.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
            return new ResponseEntity<>(us.newUser(new User(name, username, email, phone, password)),HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
