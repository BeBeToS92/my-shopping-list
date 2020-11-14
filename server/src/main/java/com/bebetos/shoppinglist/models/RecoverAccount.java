package com.bebetos.shoppinglist.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="recover_account")
@SQLDelete(sql = "UPDATE recover_account SET deleted=1 WHERE id=?")
@Where(clause = "deleted = 0")
public class RecoverAccount {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Getter @Setter Long id;

    @Getter @Setter private String email;
    @Getter @Setter private String token;

    @CreationTimestamp
    @Getter @Setter private LocalDateTime createdAt;
    @Getter @Setter private LocalDateTime validUntil;

    @Getter @Setter private Integer deleted = 0;
}