package com.dogadoptiondb.repositories;

import com.dogadoptiondb.models.Breed;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BreedRepo extends CrudRepository<Breed, Integer> {
    List<Breed> findAll();
}
