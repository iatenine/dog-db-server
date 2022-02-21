package com.dogadoptiondb.services;

import com.dogadoptiondb.app.DemoApplication;
import com.dogadoptiondb.models.Dog;
import com.dogadoptiondb.models.SavedListings;
import com.dogadoptiondb.models.User;
import com.dogadoptiondb.repositories.DogRepo;
import com.dogadoptiondb.repositories.ListingsRepo;
import com.dogadoptiondb.repositories.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = DemoApplication.class)
class DogServiceTest {
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
    private DogService mockDogService;

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

    final private User mockUser2 = new User(8,
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
            true,
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
    final private ArrayList<Dog> mockList = new ArrayList<>();

    @BeforeEach
    void setup()
    {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockUser.setDogs(new ArrayList<>());
        mockUser.getDogs().add(mockDog);

        mockUser2.setDogs(new ArrayList<>());
        mockUser2.getDogs().add(mockDog2);

        mockList.clear();
        mockList.add(mockDog);
        mockList.add(mockDog2);
    }


    @Test
    void getAllDogsNotAdopted()
    {
        Mockito.when(mockDogRepo.findAll()).thenReturn(mockList);
        Assertions.assertEquals(mockDog2.getId(),mockDogService.getAllDogsNotAdopted().get(0).getId());
    }

    @Test
    void getDog()
    {
        Mockito.when(mockDogRepo.findById(mockDog.getId())).thenReturn(Optional.of(mockDog));
        Assertions.assertEquals(mockDog.getId(),mockDogService.getDog(mockDog.getId()).getId());
    }

    @Test
    void getDogByParam()
    {
        Mockito.when(mockDogRepo.findAll()).thenReturn(mockList);
        Assertions.assertNotEquals(mockList,mockDogService.getDogByParam("false",null,null,null,null));
    }

    @Test
    void getDogsByOwnerId()
    {
        Mockito.when(mockDogRepo.findAll()).thenReturn(mockList);
        Assertions.assertEquals(mockDog.getId(),mockDogService.getDogsByOwnerId(mockUser.getId()).get(0).getId());
    }

    @Test
    void getDogApplication()
    {
        {
            ArrayList<SavedListings> fakeList = new ArrayList<>();
            fakeList.add(mockListing);
            Mockito.when(mockListingRepo.findAll()).thenReturn(fakeList);
            List<User> mockDogList = mockDogService.getDogApplication(mockDog2.getId());
            Assertions.assertEquals(mockDogList.get(0).getId(),mockUser.getId());
        }
    }

    @Test
    void newDog()
    {
        Mockito.when(mockDogRepo.save(mockDog)).thenReturn(mockDog);
        Mockito.when(mockUserRepo.findByUsername(mockUser.getUsername())).thenReturn(Optional.of(mockUser));
        Assertions.assertEquals(mockDog,mockDogService.newDog(mockUser.getUsername(),mockDog));
    }

    @Test
    void deleteDog()
    {
        Mockito.when(mockUserRepo.findByUsername(mockUser.getUsername())).thenReturn(Optional.of(mockUser));
        Assertions.assertTrue(mockDogService.deleteDog(mockDog.getId(), mockUser.getUsername()));
    }
}