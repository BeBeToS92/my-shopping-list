package com.bebetos.shoppinglist.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

import javax.persistence.*;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter private Long id;
 
    @Getter @Setter private String name;

    @ManyToMany(mappedBy = "roles")
    @Getter @Setter private Collection<User> users;
 
    @ManyToMany
    @JoinTable(
        name = "roles_privileges", 
        joinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "privilege_id", referencedColumnName = "id"))
    @Getter @Setter private Collection<Privilege> privileges;   
}