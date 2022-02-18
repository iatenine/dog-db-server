package com.dogadoptiondb.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "saved_listings")
public class SavedListings
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "users_id")
    private User owner;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "dogs_id")
    private Dog dog;
}
