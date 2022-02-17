package com.dogadoptiondb.controllers;

import com.dogadoptiondb.app.DemoApplication;
import com.dogadoptiondb.models.User;
import com.dogadoptiondb.services.UserService;
import com.google.gson.Gson;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = DemoApplication.class)

class UserControllerTest {
    private MockMvc mvc;
    private Gson gson = new Gson();
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private UserService mockUserService;

    final private User mockUser = new User(7,null,"JWT","JWTTest","JWTEmail","123313513","password");

    @BeforeEach
    void setup()
    {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    void newUser() throws Exception {
        Mockito.when(mockUserService.newUser(mockUser)).thenReturn(mockUser);

        mvc.perform(MockMvcRequestBuilders.post("/register").
                contentType(MediaType.APPLICATION_JSON).content(gson.toJson(mockUser))).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

}