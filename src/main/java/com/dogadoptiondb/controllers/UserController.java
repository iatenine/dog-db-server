package com.dogadoptiondb.controllers;

import com.dogadoptiondb.models.Dog;
import com.dogadoptiondb.models.User;
import com.dogadoptiondb.models.UserRequest;
import com.dogadoptiondb.models.UserResponse;
import com.dogadoptiondb.services.DogService;
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
    DogService ds;

    @Autowired
    JWTUtil util;

    @Autowired
    AuthenticationManager authenticationManager;


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

    @GetMapping("/users/dogs/{id}")
    public List<Dog> getDogsByOwnerId(@PathVariable("id") String id) {

        return ds.getDogsByOwnerId(Integer.parseInt(id));

    }


}
