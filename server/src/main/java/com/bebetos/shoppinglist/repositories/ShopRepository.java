package com.bebetos.shoppinglist.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bebetos.shoppinglist.models.Shop;
import com.bebetos.shoppinglist.models.User;


@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    
    List<Shop> findByUser(User user);

    Optional<Shop> findById(Long id);

    @Query(value = "SELECT ss, COUNT(ii.id), SUM(ii.isBought) " +
            "FROM Shop ss " +
            "LEFT JOIN Item ii ON ii.shop = ss AND ii.deleted = 0" +
            "WHERE ss.user = :user " +
            "GROUP BY ss.id")
    List<Object[]> findAllByUser(@Param("user") User user);
}