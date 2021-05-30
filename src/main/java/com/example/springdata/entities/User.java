package com.example.springdata.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name="id")
    private Long id;
    @Column (name = "username")
    private String username;
    @Column (name = "password")
    private String password;
    @Column (name = "name")
    private String name;
    @Column (name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns= @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User(String username, String password, String name, String email, Collection<Role> roles) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }
}
