package com.dogadoptiondb.controllers;

import com.dogadoptiondb.models.User;
import com.dogadoptiondb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController
{
    @Autowired
    UserService us;

    @PostMapping(value = "/user",consumes = "application/json",produces = "application/json")
    public User newUser(@RequestBody User u)
    {
        return us.newUser(u);
    }


}
