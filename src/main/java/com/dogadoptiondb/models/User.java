package com.dogadoptiondb.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @NonNull private String legalName;
    @NonNull private String username;
    @NonNull private String email;
    @NonNull private String phone;
    @NonNull private String password;
}
