package com.dogadoptiondb.services;

import com.dogadoptiondb.models.User;
import com.dogadoptiondb.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service

public class UserService {
    @Autowired
    UserRepo ur;

public User newUser(User u)
{
    return ur.save(u);
}

}
