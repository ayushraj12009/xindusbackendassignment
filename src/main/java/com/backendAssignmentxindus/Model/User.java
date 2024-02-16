package com.backendAssignmentxindus.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String fristName;

    private String lastName;

    private String userName;

    private String email;

    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Wishlists> wishlists = new ArrayList<>();


    public User(String s, String s1) {
        this.email = s;
        this.password = s1;

    }

    public User(String firstName, String last, String userName, String eamil, String password) {
        this.fristName = firstName;
        this.lastName = last;
        this.userName = userName;
        this.email = eamil;
        this.password = password;
    }
}
