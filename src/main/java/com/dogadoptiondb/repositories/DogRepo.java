package com.dogadoptiondb.repositories;

import com.dogadoptiondb.models.Breed;
import com.dogadoptiondb.models.Dog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DogRepo extends CrudRepository<Dog, Integer>
{
    List<Dog> findByVaccinated(boolean vaccinated);

    List<Dog> findByBreed(Breed breed);

    List<Dog> findBySize(int size);

    List<Dog> findBySex(char sex);
}
