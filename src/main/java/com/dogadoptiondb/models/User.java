package com.dogadoptiondb.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.List;

@Data
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
    private String phone;
    private String password;
}
