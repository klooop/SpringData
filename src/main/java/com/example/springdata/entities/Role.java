package com.example.springdata.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table  (name= "roles")
public class Role {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;

    @Column (name = "name")
    private String name;

    public Role(String name) {
        this.name = name;
    }
}
