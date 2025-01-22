package com.mindhub.user_microservice.models;

import jakarta.persistence.*;

@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    private RolType rolType = RolType.USER;

    public UserModel() {
    }

    public UserModel(String username, String email, String password, RolType rolType) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.rolType = rolType;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RolType getRolType() {
        return rolType;
    }

    public void setRolType(RolType rolType) {
        this.rolType = rolType;
    }
}