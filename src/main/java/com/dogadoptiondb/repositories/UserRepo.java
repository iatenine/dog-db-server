package com.dogadoptiondb.repositories;

import com.dogadoptiondb.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(int id);
}
