package com.bebetos.shoppinglist.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

import javax.persistence.*;


@Entity
public class Privilege {
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter private Long id;
 
    @Getter @Setter private String name;
 
    @ManyToMany(mappedBy = "privileges")
    @Getter @Setter private Collection<Role> roles;
}