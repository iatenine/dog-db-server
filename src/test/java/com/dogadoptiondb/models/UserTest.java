package com.dogadoptiondb.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    static private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(100);
        user.setDogs(new List<Dog>());
        user.setLegalName("legal name");
        user.setUsername("username");
        user.setEmail("email");
        user.setPhone("999-999-999");
        user.setPassword("password");
    }

    @Test
    void getId() {
    }

    @Test
    void getDogs() {
    }

    @Test
    void getLegalName() {
    }

    @Test
    void getUsername() {
    }

    @Test
    void getEmail() {
    }

    @Test
    void getPhone() {
    }

    @Test
    void getPassword() {
    }

    @Test
    void setId() {
    }

    @Test
    void setDogs() {
    }

    @Test
    void setLegalName() {
    }

    @Test
    void setUsername() {
    }

    @Test
    void setEmail() {
    }

    @Test
    void setPhone() {
    }

    @Test
    void setPassword() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void canEqual() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
    }
}