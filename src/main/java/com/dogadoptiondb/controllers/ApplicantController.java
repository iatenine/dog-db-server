package com.dogadoptiondb.controllers;

import com.dogadoptiondb.models.Dog;
import com.dogadoptiondb.models.SavedListings;
import com.dogadoptiondb.models.User;
import com.dogadoptiondb.services.DogService;
import com.dogadoptiondb.services.UserService;
import com.dogadoptiondb.util.JWTUtil;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApplicantController
{
    @Autowired
    UserService us;

    @Autowired
    DogService ds;

    @Autowired
    JWTUtil util;

    @GetMapping(path = "/applicants/dog/{id}")
    public ResponseEntity<List<User>> viewDogApplicants(@PathVariable("id") String id)
    {
        try {
            return new ResponseEntity<>(ds.getDogApplication(Integer.parseInt(id)),HttpStatus.OK);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/applicants/user/{id}")
    public ResponseEntity<List<Dog>> viewUsersApplications(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(us.getUserApplication(Integer.parseInt(id)),HttpStatus.OK);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/applicants/apply/{dogId}")
    public ResponseEntity<String> applyToAdopt(@PathVariable("dogId") String id, @RequestHeader("Authorization") String token) {

        if (token != null) {
            String username = util.getSubject(token);

            SavedListings listing = us.applyOrSave(username, Integer.parseInt(id),true);

            if (listing == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Application created successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(path = "/applicants/save/{dogId}")
    public ResponseEntity<String> saveListing(@PathVariable("dogId") String id, @RequestHeader("Authorization") String token) {

        if (token != null) {
            String username = util.getSubject(token);

            SavedListings listing = us.applyOrSave(username, Integer.parseInt(id),false);

            if (listing == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Successfully saved", HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping(path = "/applicants/approve/{userId}/{dogId}")
    public ResponseEntity<String> approveApplication(@PathVariable("userId") String userId,
                                                     @PathVariable("dogId") String dogId,
                                                     @RequestHeader("Authorization") String token) {

        if (token != null) {
            String username = util.getSubject(token);

            if (us.approveApplication(username, Integer.parseInt(userId), Integer.parseInt(dogId))) {
                return new ResponseEntity<>("Application Approved", HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


}
