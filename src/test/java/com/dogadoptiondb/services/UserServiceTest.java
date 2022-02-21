package com.dogadoptiondb.services;

import com.dogadoptiondb.app.DemoApplication;
import com.dogadoptiondb.models.Dog;
import com.dogadoptiondb.models.SavedListings;
import com.dogadoptiondb.models.User;
import com.dogadoptiondb.repositories.DogRepo;
import com.dogadoptiondb.repositories.ListingsRepo;
import com.dogadoptiondb.repositories.UserRepo;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = DemoApplication.class)
class UserServiceTest {

    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private UserRepo mockUserRepo;
    @MockBean
    private DogRepo mockDogRepo;
    @MockBean
    private ListingsRepo mockListingRepo;

    @Autowired
    private UserService mockUserService;

    @Autowired
    BCryptPasswordEncoder bCryptEncoder;



    final private User mockUser = new User(7,
            null,
            new ArrayList<>(),
            "JWT",
            "JWTTest",
            "JWTEmail",
            "123313513",
            "password");

    final private User mockUser2 = new User(7,
            null,
            new ArrayList<>(),
            "JWT2",
            "JWTTest2",
            "JWTEmail2",
            "1233135134",
            "password");
    final private Dog mockDog = new Dog(
            8,
            mockUser,
            null,
            false,
            "Bo",
            1,
            3302394,
            "",
            'M',
            false
    );

    final private Dog mockDog2 = new Dog(
            9,
            mockUser2,
            null,
            false,
            "Bob",
            1,
            3302394,
            "",
            'M',
            false
    );

    final private SavedListings mockListing = new SavedListings(
            1,
            mockUser,
            mockDog2,
            true
            );

    @BeforeEach
    void setup()
    {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockUser.setDogs(new ArrayList<>());
        mockUser.getDogs().add(mockDog);

        mockUser2.setDogs(new ArrayList<>());
        mockUser2.getDogs().add(mockDog2);

    }

    @Test
    void newUser()
    {
        User encrypted = mockUser;
        encrypted.setPassword(bCryptEncoder.encode(encrypted.getPassword()));
        Mockito.when(mockUserRepo.save(mockUser)).thenReturn(encrypted);
        Assertions.assertEquals(encrypted,mockUserService.newUser(mockUser));
    }

    @Test
    void loadUserByUsername() {
    Mockito.when(mockUserRepo.findByUsername(mockUser.getUsername())).thenReturn(Optional.of(mockUser));
        UserDetails user = mockUserService.loadUserByUsername(mockUser.getUsername());
        Assertions.assertEquals(mockUser.getUsername(),user.getUsername());
        Assertions.assertEquals(mockUser.getPassword(),user.getPassword());
        Assertions.assertEquals(user.getAuthorities().size(),1);
    }

    @Test
    void listDog()
    {
        Mockito.when(mockUserRepo.findByUsername(mockUser.getUsername())).thenReturn(Optional.of(mockUser));
        Mockito.when(mockDogRepo.save(mockUser.getDogs().get(0))).thenReturn(mockUser.getDogs().get(0));
        Dog fakeDog = mockUserService.listDog(mockUser.getUsername(), mockDog.getId(),!mockDog.isAdopted());
        Assertions.assertEquals(fakeDog,mockDog);
    }

    @Test
    void getUserApplication()
    {
        ArrayList<SavedListings> fakeList = new ArrayList<>();
        fakeList.add(mockListing);
        Mockito.when(mockListingRepo.findAll()).thenReturn(fakeList);
        List<Dog> mockDogList = mockUserService.getUserApplication(mockUser.getId());
        Assertions.assertEquals(mockDogList.get(0).getId(),mockDog2.getId());
    }

    @Test
    void applyOrSave()
    {
        Mockito.when(mockUserRepo.findByUsername(mockUser.getUsername())).thenReturn(Optional.of(mockUser));
        Mockito.when(mockDogRepo.findById(mockDog2.getId())).thenReturn(Optional.of(mockDog2));
        Mockito.when(mockListingRepo.save(new SavedListings(0, mockUser, mockDog2, true))).thenReturn(mockListing);

        SavedListings listing = mockUserService.applyOrSave(mockUser.getUsername(), mockDog2.getId(), true);
        Assertions.assertEquals(listing.getId(),mockListing.getId());
    }

    @Test
    void approveApplication()
    {
        ArrayList<SavedListings> fakeList = new ArrayList<>();
        fakeList.add(mockListing);
        Mockito.when(mockListingRepo.findByDogId(mockDog2.getId())).thenReturn(fakeList);

        Assertions.assertTrue(mockUserService.approveApplication(mockUser2.getUsername(),mockUser.getId(),mockDog2.getId()));
    }

}