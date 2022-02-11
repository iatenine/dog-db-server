package com.dogadoptiondb.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BreedTest {

    static private Breed breed;

    @BeforeEach
    void setup() {
        breed = new Breed();
        breed.setId(100);
        breed.setName("name");
        breed.setDescription("description");
        breed.setAverageWeight(30);
    }

    @Test
    void getId() {
        assertEquals(100, breed.getId());
        assertNotEquals(99, breed.getId());
    }

    @Test
    void getName() {
        assertEquals("name", breed.getName());
        assertNotEquals("other name", breed.getName());
    }

    @Test
    void getDescription() {
        assertEquals("description", breed.getDescription());
        assertNotEquals("other description", breed.getDescription());
    }

    @Test
    void getAverageWeight() {
        assertEquals(30, breed.getAverageWeight());
        assertNotEquals(25, breed.getAverageWeight());
    }

    @Test
    void setId() {
        breed.setId(99);
        assertEquals(99, breed.getId());
        assertNotEquals(100, breed.getId());
    }

    @Test
    void setName() {
        breed.setName("name2");
        assertEquals("name2", breed.getName());
        assertNotEquals("name", breed.getName());
    }

    @Test
    void setDescription() {
        breed.setDescription("new description");
        assertEquals("new description", breed.getDescription());
        assertNotEquals("description", breed.getDescription());
    }

    @Test
    void setAverageWeight() {
        breed.setAverageWeight(25);
        assertEquals(25, breed.getAverageWeight());
        assertNotEquals(30, breed.getAverageWeight());
    }

    @Test
    void testEquals() {
        Breed breed1 = breed;
        assertEquals(breed1, breed);
        Breed breed2 = new Breed();
        assertNotEquals(breed2, breed);
    }

    @Test
    void canEqual() {
        Breed b = new Breed();
        assert(b.canEqual(breed));
    }

    @Test
    void testHashCode() {
        Breed b = new Breed();
        int code = breed.hashCode();
        assertNotEquals(b.hashCode(), breed.hashCode());
        assertEquals(code, breed.hashCode());
    }

    @Test
    void testToString() {
        Breed b = new Breed();
        String breedStr = b.toString();
        assertNotEquals(breedStr, breed.toString());
        breedStr = breed.toString();
        assertEquals(breedStr, breed.toString());
    }
}