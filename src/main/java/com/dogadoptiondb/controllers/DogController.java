package com.dogadoptiondb.controllers;

import com.dogadoptiondb.models.Dog;
import com.dogadoptiondb.services.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DogController
{
    DogService ds;

    @Autowired
    public DogController(DogService ds){
        this.ds = ds;
    }

    @GetMapping("/dogs")
    public List<Dog> getAllDogsNotAdopted()
    {
        return ds.getAllDogsNotAdopted();
    }

    @GetMapping("/dogs/{id}")
    public ResponseEntity<Dog> getDog(@PathVariable("id") String id)
    {
        Dog d = ds.getDog(Integer.parseInt(id));
        if(d == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        if(d.getId() != 0) return new ResponseEntity<>(d, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/dogs/search")
    public List<Dog> getDogByParam(@RequestParam(name = "vaccination_status", required = false) String vaccinated,
                                   @RequestParam(value = "breed", required = false) String breed,
                                   @RequestParam(value = "size", required = false) String size,
                                   @RequestParam(value = "sex", required = false) String sex,
                                   @RequestParam(value = "page", required = false) String page)
    {
        return ds.getDogByParam(vaccinated,null, size, sex, page);
    }



}
