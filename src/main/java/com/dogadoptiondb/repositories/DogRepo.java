package com.dogadoptiondb.repositories;

import com.dogadoptiondb.models.Breed;
import com.dogadoptiondb.models.Dog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogRepo extends CrudRepository<Dog, Integer>
{
    List<Dog> findByVaccinatedAndBreedAndSizeAndSex(boolean vaccinated, Breed breed, int size, char sex);
}
