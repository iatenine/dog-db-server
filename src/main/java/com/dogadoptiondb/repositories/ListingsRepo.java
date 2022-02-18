package com.dogadoptiondb.repositories;

import com.dogadoptiondb.models.Dog;
import com.dogadoptiondb.models.SavedListings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListingsRepo extends CrudRepository<SavedListings, Integer>
{
    List<SavedListings> findByDogId(int id);

}
