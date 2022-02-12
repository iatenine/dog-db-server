package com.dogadoptiondb.controllers;

import com.dogadoptiondb.models.Dog;
import com.dogadoptiondb.services.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DogController
{
    @Autowired
    DogService ds;

    @GetMapping("/dogs")
    public List<Dog> getAllDogsNotAdopted()
    {
        return ds.getAllDogsNotAdopted();
    }

    @GetMapping("/dogs/{id}")
    public ResponseEntity<Dog> getDog(@PathVariable("id") String id)
    {
        Dog d = ds.getDog(Integer.parseInt(id));

        if(d.getId() != 0) return new ResponseEntity<>(d, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
