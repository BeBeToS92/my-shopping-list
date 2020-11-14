package com.bebetos.shoppinglist.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="shops")
@SQLDelete(sql = "UPDATE shops SET deleted=1 WHERE id=?")
@Where(clause = "deleted = 0")
public class Shop {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Getter @Setter private Long id;

    @Getter @Setter private String name;

    @ManyToOne
    @JsonIgnore
    @Getter @Setter private User user;

    @CreationTimestamp
    @Getter @Setter private LocalDateTime createdAt;
    @UpdateTimestamp
    @Getter @Setter private LocalDateTime updatedAt;
    
    @Getter @Setter private Integer deleted = 0;

    @Transient
    @Getter @Setter private Long total = 0L;

    @Transient
    @Getter @Setter private Long bought = 0L;

    @Transient
    @Getter @Setter private List<Item> items = new ArrayList<Item>();
}