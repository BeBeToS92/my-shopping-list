package com.bebetos.shoppinglist.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.*;


@Entity
@Table(name="users")
@SQLDelete(sql = "UPDATE users SET deleted=1 WHERE id=?")
@Where(clause = "deleted = 0")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Getter @Setter private Long id;

    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Getter @Setter private String email;

    @JsonIgnore
    @Getter @Setter private String password;
    
    @Getter @Setter private boolean enabled;

    @CreationTimestamp
    @Getter @Setter private LocalDateTime createdAt;
    @UpdateTimestamp
    @Getter @Setter private LocalDateTime updatedAt;
    
    @Getter @Setter private Integer deleted = 0;

    @ManyToMany
    @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id")) 
    @Getter @Setter private Collection<Role> roles;
}