package com.dogadoptiondb.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    static private User user;

    @BeforeEach
    void setup() {

        List<Dog> dogs = new ArrayList<>();

        dogs.add(new Dog());
        dogs.add(new Dog());
        dogs.add(new Dog());

        user = new User();
        user.setId(100);
        user.setDogs(dogs);
        user.setLegalName("legal name");
        user.setUsername("username");
        user.setEmail("email");
        user.setPhone("999-999-999");
        user.setPassword("password");
    }

    @Test
    void getId() {
        assertEquals(100, user.getId());
        assertNotEquals(50, user.getId());
    }

    @Test
    void getDogs() {
        List<Dog> otherDogs = user.getDogs();
        assertEquals(otherDogs, user.getDogs());
        otherDogs = new ArrayList<>();
        assertNotEquals(otherDogs, user.getDogs());
    }

    @Test
    void getLegalName() {
        assertEquals("legal name", user.getLegalName());
        assertNotEquals("other name", user.getLegalName());
    }

    @Test
    void getUsername() {
        assertEquals("username", user.getUsername());
        assertNotEquals("other name", user.getUsername());
    }

    @Test
    void getEmail() {
        assertEquals("email", user.getEmail());
        assertNotEquals("other email", user.getEmail());
    }

    @Test
    void getPhone() {
        assertEquals("999-999-999", user.getPhone());
        assertNotEquals("123-456-789", user.getPhone());
    }

    @Test
    void getPassword() {
        assertEquals("password", user.getPassword());
        assertNotEquals("other password", user.getPassword());
    }

    @Test
    void setId() {
        assertEquals("999-999-999", user.getPhone());
        assertNotEquals("123-456-789", user.getPhone());
    }


    //Todo---------Needs Better Test
    @Test
    void setDogs() {
        List<Dog> oldDogs = user.getDogs();
        List<Dog> newDogs = new ArrayList<>();
        newDogs.add(new Dog());
        newDogs.add(new Dog());
        newDogs.add(new Dog());
        user.setDogs(newDogs);

        assertEquals(newDogs, user.getDogs());
//        assertNotEquals(oldDogs, user.getDogs());
    }

    @Test
    void setLegalName() {
        user.setLegalName("John Doe");
        assertEquals("John Doe", user.getLegalName());
        assertNotEquals("legal name", user.getLegalName());
    }

    @Test
    void setUsername() {
        user.setUsername("newUsername");
        assertEquals("newUsername", user.getUsername());
        assertNotEquals("username", user.getUsername());
    }

    @Test
    void setEmail() {
        user.setEmail("newEmail");
        assertEquals("newEmail", user.getEmail());
        assertNotEquals("email", user.getEmail());
    }

    @Test
    void setPhone() {
        user.setPhone("123-456-789");
        assertEquals("123-456-789", user.getPhone());
        assertNotEquals("999-999-999", user.getPhone());
    }

    @Test
    void setPassword() {
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
        assertNotEquals("password", user.getPassword());
    }

    @Test
    void testEquals() {
        User user2 = user;
        assertEquals(user2, user);
        User user3 = new User();
        assertNotEquals(user3, user);
    }

    @Test
    void canEqual() {
        User user2 = new User();
        assert(user2.canEqual(user));
    }

    @Test
    void testHashCode() {
        User user2 = new User();
        int code = user.hashCode();
        assertNotEquals(user2.hashCode(), user.hashCode());
        assertEquals(code, user.hashCode());
    }

    @Test
    void testToString() {
        User user2 = new User();
        String userStr = user2.toString();
        assertNotEquals(userStr, user.toString());
        userStr = user.toString();
        assertEquals(userStr, user.toString());
    }
}