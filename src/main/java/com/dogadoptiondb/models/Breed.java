package com.dogadoptiondb.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "breeds")
public class Breed
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    private String name;
    private String description;
    private int averageWeight;
}
