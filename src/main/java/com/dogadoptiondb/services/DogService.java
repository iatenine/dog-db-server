
package com.dogadoptiondb.services;

import com.dogadoptiondb.models.Breed;
import com.dogadoptiondb.models.Dog;
import com.dogadoptiondb.repositories.DogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.*;

import java.util.List;
@Service
public class DogService
{
    @Autowired
    DogRepo dr;

    public List<Dog> getAllDogsNotAdopted()
    {
        List<Dog> dogs = (List<Dog>) dr.findAll();
        return dogs.stream().filter(dog -> !dog.isAdopted()).collect(Collectors.toList());
    }
    public Dog getDog(int id)
    {
        return dr.findById(id).orElse(new Dog());
    }


    public List<Dog> getDogByParam(boolean vaccinated, Breed breed, int size, char sex)
    {
        return dr.findByVaccinatedAndBreedAndSizeAndSex(vaccinated,
                breed,
                size,
                sex)
                .stream().filter(dog ->!dog.isAdopted()).collect(Collectors.toList());
    }
}
