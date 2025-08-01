// src/main/java/com/example/CalorieCalcu/models/RegisterDetails.java
package com.example.CalorieCalcu.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user_details")
public class RegisterDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; // Changed from int to Long

    @Column(nullable = false)
    private String name;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(name = "user_name",nullable = false,unique=true)
    private String userName;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name= "user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "roleId")
    )
    private Set<Roles>roles;


    public Long getUserId() { // Changed return type
        return userId;
    }

    public void setUserId(Long userId) { // Changed parameter type
        this.userId = userId;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public void setRoleNames(Set<Roles> roleNames) {
    }
}