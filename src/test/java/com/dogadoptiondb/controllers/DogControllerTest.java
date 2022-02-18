package com.dogadoptiondb.controllers;

import com.dogadoptiondb.app.DemoApplication;
import com.dogadoptiondb.models.Dog;
import com.dogadoptiondb.services.DogService;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;

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
            13,
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
            2,
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
            8,
            null,
            null,
            false,
            "Bo",
            1,
            3302394,
            "",
            'M',
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
    void getAllDogsNotAdopted() throws Exception {
        Mockito.when(mockDogService.getAllDogsNotAdopted()).thenReturn(
                mockDogList
        );
        mvc.perform(MockMvcRequestBuilders.get("/dogs").accept(
                        MediaType.APPLICATION_JSON
                ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        mvc.perform(MockMvcRequestBuilders.get("/hunds").accept(
                        MediaType.APPLICATION_JSON
                ))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    void getDog() throws Exception {
        Mockito.when(mockDogService.getDog(302)).thenReturn(
                new Dog()
        );
        Mockito.when(mockDogService.getDog(2)).thenReturn(
                mockDog3
        );
            mvc.perform(MockMvcRequestBuilders.get("/dogs/302"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());

            mvc.perform(MockMvcRequestBuilders.get("/dogs/2"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Bo"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.vaccinated").isBoolean());

    }

    @Test
    void getDogByParam() throws Exception {
        LinkedList<Dog> vaccinatedList = new LinkedList<>();
        vaccinatedList.add(mockDog2);
        Mockito.when(mockDogService.getDogByParam("true", null, null, null, null)).thenReturn(
                vaccinatedList
        );
        Mockito.when(mockDogService.getDogByParam("false", null, null, "f", null)).thenReturn(
                mockDogList.stream().
                        filter(
                                dog -> dog.isVaccinated() && 'F' == dog.getSex()
                        )
                        .collect(Collectors.toList())
        );
        ResultActions ra = mvc.perform(MockMvcRequestBuilders.get("/dogs?vaccinated=true"));
        ra.andExpect(MockMvcResultMatchers.status().isOk());
        ra.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        ra.andExpect(MockMvcResultMatchers.jsonPath("$", isA(net.minidev.json.JSONArray.class)));

        mvc.perform(MockMvcRequestBuilders.get("/dogs?vaccinated=false&sex=f"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", isA(net.minidev.json.JSONArray.class)));
    }

}