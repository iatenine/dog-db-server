package com.dogadoptiondb.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class DogTest {

    static private Dog dog;

    @BeforeEach
    void setup() {
        dog = new Dog();
        dog.setId(100);
        dog.setOwnersID(200);
        dog.setBreedID(300);
        dog.setAdopted(true);
        dog.setSize(1);
        dog.setDob(400);
        dog.setSrc("/image/source");
        dog.setSex('M');
        dog.setVaccinated(true);
    }


    @Test
    void getId() {

        assertEquals(100, dog.getId());
        assertNotEquals(50, dog.getId());
    }

    @Test
    void getOwnersID() {
        assertEquals(200, dog.getOwnersID());
        assertNotEquals(100, dog.getOwnersID());
    }

    @Test
    void getBreedID() {
        assertEquals(300, dog.getBreedID());
        assertNotEquals(150, dog.getBreedID());
    }

    @Test
    void isAdopted() {
        assertTrue(dog.isAdopted());
        assertNotEquals(false, dog.isAdopted());
    }

    @Test
    void getSize() {
        assertEquals(1, dog.getSize());
        assertNotEquals(2, dog.getSize());
    }

    @Test
    void getDob() {
        assertEquals(400, dog.getDob());
        assertNotEquals(200, dog.getDob());
    }

    @Test
    void getSrc() {
        assertEquals("/image/source", dog.getSrc());
        assertNotEquals("/", dog.getSrc());
    }

    @Test
    void getSex() {
        assertEquals('M', dog.getSex());
        assertNotEquals('F', dog.getSex());
    }

    @Test
    void isVaccinated() {
        assertTrue(dog.isAdopted());
        assertNotEquals(false, dog.isAdopted());
    }

    @Test
    void setId() {
        dog.setId(99);
        assertEquals(99, dog.getId() );
        assertNotEquals(100, dog.getId());
    }

    @Test
    void setOwnersID() {
        dog.setOwnersID(299);
        assertEquals(299, dog.getOwnersID() );
        assertNotEquals(100, dog.getOwnersID());
    }

    @Test
    void setBreedID() {
        dog.setBreedID(2);
        assertEquals(2, dog.getBreedID() );
        assertNotEquals(1, dog.getBreedID());
    }

    @Test
    void setAdopted() {
        dog.setAdopted(false);
        assertFalse(dog.isAdopted());
        assertNotEquals(true, dog.isAdopted());
    }

    @Test
    void setSize() {
        dog.setSize(2);
        assertEquals(2, dog.getSize() );
        assertNotEquals(1, dog.getSize());
    }

    @Test
    void setDob() {
        dog.setDob(399);
        assertEquals(399, dog.getDob() );
        assertNotEquals(100, dog.getDob());
    }

    @Test
    void setSrc() {
        dog.setSrc("/new/image/source");
        assertEquals("/new/image/source", dog.getSrc() );
        assertNotEquals("/image/source", dog.getSrc());
    }

    @Test
    void setSex() {
        dog.setSex('F');
        assertEquals('F', dog.getSex() );
        assertNotEquals('M', dog.getSex());
    }

    @Test
    void setVaccinated() {
        dog.setVaccinated(false);
        assertFalse(dog.isVaccinated());
        assertNotEquals(true, dog.isVaccinated());
    }

    @Test
    void testEquals() {
        Dog d2 = dog;
        assertEquals(d2, dog);
        Dog d3 = new Dog();
        assertNotEquals(d3, dog);
    }

    @Test
    void canEqual() {
        Dog d = new Dog();
        assert(d.canEqual(dog));
    }

    @Test
    void testHashCode() {
        Dog d = new Dog();
        int code = dog.hashCode();
        assertNotEquals(d.hashCode(), dog.hashCode());
        assertEquals(code, dog.hashCode());
    }

    @Test
    void testToString() {
        Dog d = new Dog();
        String dogStr = d.toString();
        assertNotEquals(dogStr, dog.toString());
        dogStr = dog.toString();
        assertEquals(dogStr, dog.toString());
    }
}