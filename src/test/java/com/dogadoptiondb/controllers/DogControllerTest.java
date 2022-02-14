package com.dogadoptiondb.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(DogController.class)
class DogControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getAllDogsNotAdopted() {
//        mvc.perform(
//                    MockMvcRequestBuilders.
//                            get("/dogs").
//                            accept(MediaType.APPLICATION_JSON)
//        );
    }

    @Test
    void getDog() {
    }

    @Test
    void getDogByParam() {
    }
}