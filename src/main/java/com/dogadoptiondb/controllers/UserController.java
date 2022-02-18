package com.dogadoptiondb.controllers;

import com.dogadoptiondb.models.Dog;
import com.dogadoptiondb.models.User;
import com.dogadoptiondb.models.UserRequest;
import com.dogadoptiondb.models.UserResponse;
import com.dogadoptiondb.services.UserService;
import com.dogadoptiondb.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UserController
{
    @Autowired
    UserService us;

    @Autowired
    JWTUtil util;

    @Autowired
    AuthenticationManager authenticationManager;


    @GetMapping(value = "/user/dogs/{id}")
    public ResponseEntity<List<Dog>> getPublicUsersDogs(@PathVariable("id") String id)
    {
        try {
            List<Dog> dogs = us.getUsersDogs(Integer.parseInt(id));

            if (dogs == (null))
            {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(dogs, HttpStatus.OK);
        }
        catch (NumberFormatException e)
        {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/register",consumes = "application/json",produces = "application/json")
    public ResponseEntity<UserResponse> newUser(@RequestBody User u)
    {
        User created = us.newUser(u);

        if (created.getId()!=0) {
            String token =util.generateToken(created.getUsername());
            return ResponseEntity.ok(new UserResponse(token, "Token generated successfully"));
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest request){

        //Validate username/password with DB(required in case of Stateless Authentication)
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));
        String token =util.generateToken(request.getUsername());
        return ResponseEntity.ok(new UserResponse(token,"Token generated successfully!"));
    }

}
/*
https://revature.zoom.us/j/93959051560?pwd=bldtaldtK0wyelJkUU9WVHg0d3pSQT09
 */
