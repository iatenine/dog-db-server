
package com.dogadoptiondb.services;

import com.dogadoptiondb.models.Breed;
import com.dogadoptiondb.models.Dog;
import com.dogadoptiondb.models.SavedListings;
import com.dogadoptiondb.models.User;
import com.dogadoptiondb.repositories.DogRepo;
import com.dogadoptiondb.repositories.ListingsRepo;
import com.dogadoptiondb.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.*;

import java.util.List;
@Service
public class DogService {
    @Autowired
    DogRepo dr;

    @Autowired
    UserRepo ur;

    @Autowired
    ListingsRepo lr;

    /**
     * Gets all dogs that are not adopted
     * @return a list of all non adopted dogs
     */
    public List<Dog> getAllDogsNotAdopted() {
        List<Dog> dogs = (List<Dog>) dr.findAll();
        return dogs.stream().filter(dog -> !dog.isAdopted()).collect(Collectors.toList());
    }

    public Dog getDog(int id) {
        return dr.findById(id).orElse(new Dog());
    }


    /**
     * returns a list of dogs based on search parameters
     * @param vaccinated vaccination status
     * @param breed breed
     * @param size dog size as a number
     * @param sex dogs sex
     * @param page page the dog is located on
     * @return a list of dogs matching the search criteria
     */
    public List<Dog> getDogByParam(String vaccinated, Breed breed, String size, String sex, String page) {
        List<Dog> dogs = (List<Dog>) dr.findAll();
        dogs = dogs.stream().filter(dog -> !dog.isAdopted()).collect(Collectors.toList());
        Optional<String> vaccinatedOptional = Optional.ofNullable(vaccinated);
        Optional<Breed> breedOptional = Optional.ofNullable(breed);
        Optional<String> sizeOptional = Optional.ofNullable(size);
        Optional<String> sexOptional = Optional.ofNullable(sex);
        Optional<String> pageOptional = Optional.ofNullable(page);
        if (breedOptional.isPresent()) {
            dogs.retainAll(dr.findByVaccinated(Boolean.parseBoolean(vaccinated)));}
            if (vaccinatedOptional.isPresent()) {
                dogs.retainAll(dr.findByVaccinated(Boolean.parseBoolean(vaccinated.toUpperCase())));
            }
            if (breedOptional.isPresent()) {
                dogs.retainAll(dr.findByBreed(breed));
            }
            if (sizeOptional.isPresent()) {
                dogs.retainAll(dr.findBySize(Integer.parseInt(size)));
            }
            if (sexOptional.isPresent()) {
                dogs.retainAll(dr.findBySex(sex.toUpperCase().charAt(0)));
            }
            if (pageOptional.isPresent()) {
                int startIndex = Integer.parseInt(page) * 10;
                int listSize = dogs.size();
                // Return empty list if pagination OOB
                if (startIndex - 10 > listSize || startIndex < 10)
                    return dogs.stream().filter((dog) -> false).collect(Collectors.toList());
                return dogs.subList(startIndex - 10, Math.min(listSize, startIndex));
            }
            return dogs;
    }

    /**
     * gets all dogs owned by a user
     * @param id id of the owner being searched
     * @return all dogs owned by the user with the matching id
     */
    public List<Dog> getDogsByOwnerId(int id) {


        List<Dog> dogs = (List<Dog>) dr.findAll();

        return dogs.stream().filter(dog -> dog.getOwner().getId() == id).collect(Collectors.toList());
    }

    /**
     * gets all users with an application for a dog
     * @param id dog being searched for
     * @return all users with an application for the dog
     */
    public List<User> getDogApplication(int id)
    {
        List<SavedListings> apps = (List<SavedListings>) lr.findAll();
        apps = apps.stream().filter(listing -> listing.getDog().getId()==id).collect(Collectors.toList());

        ArrayList<User> users= new ArrayList<>();
        for (SavedListings listing:apps)
        {
            users.add(listing.getOwner());
        }
        return users;
    }

    /**
     * creates a new dog
     * @param username username of the owner of the dog
     * @param d dog being created
     * @return the created dog
     */
    public Dog newDog(String username, Dog d)
    {
        d.setOwner(ur.findByUsername(username).get());
        return dr.save(d);
    }

    /**
     * deletes a dog
     * @param id dogs id being deleted
     * @param username username of the dogs owner
     * @return true if dog was deleted, false if it was not deleted
     */
    public boolean deleteDog(int id, String username) {

        User u = ur.findByUsername(username).orElse(null);

        if (u == null) return false;

        if(u.getDogs().stream().anyMatch(dog -> dog.getId() == id)) {
            dr.deleteById(id);

            return true;
        }
        return false;
    }

}

