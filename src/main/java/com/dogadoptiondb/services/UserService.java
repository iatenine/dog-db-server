package com.dogadoptiondb.services;

import com.dogadoptiondb.models.Dog;
import com.dogadoptiondb.models.User;
import com.dogadoptiondb.repositories.DogRepo;
import com.dogadoptiondb.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class UserService implements UserDetailsService {
    @Autowired
    UserRepo ur;

    @Autowired
    DogRepo dr;

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

        public Dog listDog(String username, int id)
        {
            Optional<User> u = ur.findByUsername(username);
            if(u.isEmpty()) {
                throw new UsernameNotFoundException("User with username: " +username +" not found");
            }else {
               Dog d = u.get().getDogs().stream().filter(dog -> dog.getId()==id).collect(Collectors.toList()).get(0);
               d.setAdopted(false);
               dr.save(d);
                return d;
            }
        }
    }
