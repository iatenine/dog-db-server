package com.dogadoptiondb.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    @OneToMany(mappedBy = "owner")
    private List<Dog> dogs;

    private String legalName;
    private String username;
    private String email;
    @Column(columnDefinition = "numeric")
    private String phone;
    private String password;
}
