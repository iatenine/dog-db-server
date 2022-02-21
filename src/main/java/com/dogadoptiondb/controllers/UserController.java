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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
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

    @Autowired
    UserDetailsService detailsService;


    @PostMapping(value = "/register")
    public ResponseEntity<UserResponse> newUser(@RequestBody User u)
    {
        User created = us.newUser(u);

        if (created.getId()!=0) {
            String token =util.generateToken(created.getUsername());
            return ResponseEntity.ok(new UserResponse(token, "Token generated successfully"));
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/users/listDog/{id}")
    public ResponseEntity<String> listDog(@PathVariable("id") String id, @RequestHeader("Authorization") String token)
    {
        try {
            if (token != null) {
                String username = util.getSubject(token);
                Dog d = us.listDog(username, Integer.parseInt(id), false);
                return new ResponseEntity<>(d.getName() + " has been successfully listed", HttpStatus.OK);
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
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

    @GetMapping("/users/mydogs")
    public ResponseEntity<List<Dog>> getMyDogs(@RequestHeader("Authorization") String token) {

        if (token != null) {
            String username = util.getSubject(token);
            User u = us.getUserByUsername(username);
            if(u == null)
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(u.getDogs(), HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @PostMapping("/users/newdog")
    public ResponseEntity<Dog> newDog(@RequestBody Dog dog, @RequestHeader("Authorization") String token)
    {
        if (token != null) {
            String username = util.getSubject(token);
            Dog d = ds.newDog(username,dog);
            if (d.getId() != 0) {
                return new ResponseEntity<>(d, HttpStatus.OK);
            } else return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/users/removelisting/{id}")
    public ResponseEntity<String> deList(@PathVariable("id") String id, @RequestHeader("Authorization") String token)
    {
        try {
            if (token != null) {
                String username = util.getSubject(token);
                Dog d = us.listDog(username, Integer.parseInt(id), true);
                return new ResponseEntity<>(d.getName() + " has been successfully de-listed", HttpStatus.OK);
            }
            else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}
