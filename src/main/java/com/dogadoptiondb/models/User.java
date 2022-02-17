package com.dogadoptiondb.models;

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
    private List<Dog> dogs;

    @NonNull private String legalName;
    @NonNull private String username;
    @NonNull private String email;
    @NonNull private String phone;
    @NonNull private String password;
}
