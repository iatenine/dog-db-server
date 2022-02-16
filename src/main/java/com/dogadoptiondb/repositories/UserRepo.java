package com.dogadoptiondb.repositories;

import com.dogadoptiondb.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Integer> {
}
