package com.bebetos.shoppinglist.repositories;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bebetos.shoppinglist.models.Item;
import com.bebetos.shoppinglist.models.Shop;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    
    List<Item> findAllByShop(Shop shop);

    @Query("SELECT COUNT(i.id), SUM(i.isBought) " +
           " FROM Item i" +
           " WHERE i.shop = :shop " +
           " GROUP BY item.shop LIMIT 1")
    Long[] findTotalAndBoughtByShop(@Param("shop") Shop shop);

}