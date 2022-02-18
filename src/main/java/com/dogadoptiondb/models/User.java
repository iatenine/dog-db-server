package com.dogadoptiondb.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "users")


public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    @OneToMany(mappedBy = "owner")
    @JsonManagedReference
    private List<Dog> dogs;

    @ManyToMany
    @JoinTable(
            name = "saved_listings",
            joinColumns = {
                    @JoinColumn(name = "users_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "dogs_id")
            }
    )
    private List<Dog> savedDogs;

    @NonNull private String legalName;
    @NonNull private String username;
    @NonNull private String email;
    @NonNull private String phone;
    @NonNull private String password;
}
