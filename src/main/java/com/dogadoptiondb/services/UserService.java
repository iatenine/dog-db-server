package com.dogadoptiondb.services;

import com.dogadoptiondb.models.User;
import com.dogadoptiondb.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service

public class UserService implements UserDetailsService {
    @Autowired
    UserRepo ur;

    @Autowired
    BCryptPasswordEncoder bCryptEncoder;

public User newUser(User u)
{
    u.setPassword(bCryptEncoder.encode(u.getPassword()));
        return ur.save(u);
}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<User> opt = ur.findByUsername(username);


        org.springframework.security.core.userdetails.User springUser;
            if(opt.isEmpty()) {
                throw new UsernameNotFoundException("User with username: " +username +" not found");
            }else {
                User user =opt.get();	//retrieving user from DB

                Set<GrantedAuthority> ga = new HashSet<>();
                ga.add(new SimpleGrantedAuthority("User"));
                springUser = new org.springframework.security.core.userdetails.User(
                        username,
                        user.getPassword(),
                        ga);
            }

            return springUser;
        }
    }
