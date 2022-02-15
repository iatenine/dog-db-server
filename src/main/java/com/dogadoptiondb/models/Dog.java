package com.dogadoptiondb.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dogs")
public class Dog
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "owners_id")
    private User owner;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "breed")
    private Breed breed;

    private boolean adopted;
    private String name;
    private int size;
    private long dob;
    @Column(name = "img_src")
    private String src;

    @Column(columnDefinition = "VARCHAR(1)")
    private char sex;
    private boolean vaccinated;

}
