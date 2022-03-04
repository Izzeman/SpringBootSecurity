package com.example.springboot.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Enumerated(value=EnumType.STRING)
    @Column(name="role")
    private Role role;

    @Enumerated(value=EnumType.STRING)
    @Column(name="status")
    private Status status;

    public User(){}

    public User(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString(){

        return String.format("id - %d, %s %s, email - %s, role - %s, status - %s",
                id, firstName, lastName, email, role.name(), status.name());
    }
}
