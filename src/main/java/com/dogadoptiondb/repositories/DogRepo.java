package com.dogadoptiondb.repositories;

import com.dogadoptiondb.models.Dog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogRepo extends CrudRepository<Dog, Integer>
{

}
