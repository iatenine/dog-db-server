package com.dogadoptiondb.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
public class Dog
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int ownersID;
    private int breedID;
    private boolean adopted;
    private int size;
    private long dob;
    private String src;
    private char sex;
    private boolean vaccinated;

}
