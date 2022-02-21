package com.dogadoptiondb.services;

import com.dogadoptiondb.models.Dog;
import com.dogadoptiondb.models.SavedListings;
import com.dogadoptiondb.models.User;
import com.dogadoptiondb.repositories.DogRepo;
import com.dogadoptiondb.repositories.ListingsRepo;
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

import java.util.*;
import java.util.stream.Collectors;

@Service

public class UserService implements UserDetailsService {
    @Autowired
    UserRepo ur;

    @Autowired
    DogRepo dr;

    @Autowired
    ListingsRepo lr;

    @Autowired
    BCryptPasswordEncoder bCryptEncoder;

    /**
     * Takes in a user and encrypts the password before being inserted into the DB
     * @param u user being created
     * @return the created user
     */
    public User newUser(User u)
{
    u.setPassword(bCryptEncoder.encode(u.getPassword()));
        return ur.save(u);
    }

    public User getUserByUsername(String username){
        return ur.findByUsername(username).orElse(null);
    }

    /**
     * Uses a username to create a spring user that is used by JWT
     * @param username username being searched for
     * @return UserDetails of the user that was found
     * @throws UsernameNotFoundException thrown if the username does not exist
     */
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

    /**
     * Lists or removes a listing for a dog
     * @param username username of the user listing the dog
     * @param id id of the dog
     * @param adopted true if the dog is being listed, false if the listing is being removed
     * @return the dog that has been changed
     */
        public Dog listDog(String username, int id, boolean adopted)
        {
            Optional<User> u = ur.findByUsername(username);
            if(u.isEmpty()) {
                throw new UsernameNotFoundException("User with username: " +username +" not found");
            }else {
               Dog d = u.get().getDogs().stream().filter(dog -> dog.getId()==id).collect(Collectors.toList()).get(0);
               d.setAdopted(adopted);
               dr.save(d);
                return d;
            }
        }

    /**
     * Returns a list of all dogs that the user has an application for or has saved
     * @param id id of the user
     * @return list of dogs the user has applied for or saved
     */
    public List<Dog> getUserApplication(int id)
    {
        List<SavedListings> apps = (List<SavedListings>) lr.findAll();
        apps = apps.stream().filter(listing -> listing.getOwner().getId()==id).collect(Collectors.toList());

        ArrayList<Dog> dogs = new ArrayList<>();
        for (SavedListings listing:apps)
        {
            dogs.add(listing.getDog());
        }
        return dogs;
    }

    /**
     * Allows a user to apply for or save a dog to their favorites list
     * @param username user that is applying/saving
     * @param id the dogs id that is being applied for / saved
     * @param isApplying true if it is an application, false if the dog is just being saved
     * @return the created listing
     */
    public SavedListings  applyOrSave(String username, int id, boolean isApplying) {

        User user = ur.findByUsername(username).orElse(null);
        Dog dog = dr.findById(id).orElse(null);

        if ( user != null && dog != null) {

            SavedListings listing = new SavedListings(0, user, dog, isApplying);

            return lr.save(listing);
        }
        return null;
    }

    /**
     * Approves an existing dog adoption application
     * @param username Username of the approving user
     * @param userId Id of the user that is going to adopt the dog
     * @param dogId Id of the dog being adopted
     * @return true if successful, false if it failed
     */
    public boolean approveApplication(String username, int userId, int dogId) {

        List<SavedListings> listingsList = lr.findByDogId(dogId).stream().filter(
                listingItem -> listingItem.getDog().getOwner().getUsername().equals(username)
                && listingItem.isApplicant()
                && listingItem.getOwner().getId() == userId)
                .collect(Collectors.toList());

        if(listingsList.isEmpty()) return false;

        SavedListings list =  listingsList.get(0);

        Dog dog = list.getDog();

        List<SavedListings> l = lr.findByDogId(dogId);

        lr.deleteAll(l);

        dog.setOwner(list.getOwner());
        dog.setAdopted(true);

        dr.save(dog);

        return true;
    }

    }
