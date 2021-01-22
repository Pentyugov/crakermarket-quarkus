package com.netkreker.crackermarket.model;

import com.netkreker.crackermarket.model.core.BaseEntity;
import io.quarkus.panache.common.Parameters;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class User extends BaseEntity {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private LocalDate birthDate;
    private String role;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public static User findById(String id) {
        return find("id", id).firstResult();
    }

    public static User findByUsername(String username) {
        return find("username", username).firstResult();
    }

    public static List<User> findByStatus(String status) {
        return find("status", status.toUpperCase()).list();
    }

    public static List<User> findByRole(String role) {
        return find("role", role.toUpperCase()).list();
    }

    public static List<User> findByParams(String status, String role) {
        return find("status = :status and role = :role",
                Parameters.with("status", status.toUpperCase()).and("role", role.toUpperCase())).list();
    }
}