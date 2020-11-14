package com.bebetos.shoppinglist.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="items")
@SQLDelete(sql = "UPDATE items SET deleted=1 WHERE id=?")
@Where(clause = "deleted = 0")
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Getter @Setter private Long id;

    @Getter @Setter private String name;

    @Getter @Setter private Integer isBought = 0;

    @Getter @Setter private Integer unavailable = 0;

    @ManyToOne
    @JsonIgnore
    @Getter @Setter private Shop shop;

    @CreationTimestamp
    @Getter @Setter private LocalDateTime createdAt;
    @UpdateTimestamp
    @Getter @Setter private LocalDateTime updatedAt;
    
    @Getter @Setter private Integer deleted = 0;
    
}
