package com.dogadoptiondb.controllers;

import com.dogadoptiondb.models.Dog;
import com.dogadoptiondb.models.User;
import com.dogadoptiondb.services.DogService;
import com.dogadoptiondb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class ApplicantController
{
    @Autowired
    UserService us;

    @Autowired
    DogService ds;

    @GetMapping(name = "/applicants/dog/{id}")
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

    @GetMapping(name = "/applicants/user/{id}")
    public ResponseEntity<List<Dog>> viewUsersApplications(@PathVariable("id") String id)
    {
        return null;
    }
}
