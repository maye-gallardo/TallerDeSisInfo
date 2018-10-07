package com.teacherselection.teacherselectionservice.entities;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Users{
    @Id
    private String id;

    private String firstname;
    private String lastname;

    private String email;
    private String password;

    @DBRef
    private Set<Role> roles;

    public Users(){
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setFirstname(String firstName) {
        this.firstname = firstName;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setLastname(String lastName) {
        this.lastname = lastName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }
    
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}