package com.dogadoptiondb.controllers;

import com.dogadoptiondb.app.DemoApplication;
import com.dogadoptiondb.models.Dog;
import com.dogadoptiondb.services.DogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.LinkedList;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = DemoApplication.class)
class DogControllerTest {
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private DogService mockDogService;

    final private LinkedList<Dog> mockDogList = new LinkedList<>();

    final private Dog mockDog1 = new Dog(
            0,
            null,
            null,
            false,
            "Pickles",
            1,
            3302394,
            "",
            'F',
            false
    );

    final private Dog mockDog2 = new Dog(
            0,
            null,
            null,
            false,
            "Ladybird",
            2,
            334502394,
            "",
            'F',
            true
    );

    final private Dog mockDog3 = new Dog(
            0,
            null,
            null,
            false,
            "Pickles",
            1,
            3302394,
            "",
            'F',
            false
    );

    @BeforeEach
    void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockDogList.add(mockDog1);
        mockDogList.add(mockDog2);
        mockDogList.add(mockDog3);
    }

    @Test
    void getAllDogsNotAdopted() {
        Mockito.when(mockDogService.getAllDogsNotAdopted()).thenReturn(
                mockDogList
        );
        try {
            mvc.perform(MockMvcRequestBuilders.get("/dogs").accept(
                            MediaType.APPLICATION_JSON
                    ))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            mvc.perform(MockMvcRequestBuilders.get("/hunds").accept(
                            MediaType.APPLICATION_JSON
                    ))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getDog() {
        Mockito.when(mockDogService.getDog(0)).thenReturn(
                mockDog1
        );
        Mockito.when(mockDogService.getDog(1)).thenReturn(
                mockDog2
        );
        Mockito.when(mockDogService.getDog(2)).thenReturn(
                mockDog3
        );
        Mockito.when(mockDogService.getDog(4)).thenReturn(
                new Dog()
        );
        try {
            mvc.perform(MockMvcRequestBuilders.get("/dogs/2").accept(
                            MediaType.APPLICATION_JSON
                    ))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            mvc.perform(MockMvcRequestBuilders.get("/hunds").accept(
                            MediaType.APPLICATION_JSON
                    ))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getDogByParam() {
    }
}