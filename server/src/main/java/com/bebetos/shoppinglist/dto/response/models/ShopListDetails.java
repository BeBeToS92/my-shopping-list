package com.bebetos.shoppinglist.dto.response.models;

import com.bebetos.shoppinglist.models.Shop;

import lombok.Getter;
import lombok.Setter;

public class ShopListDetails {

    @Getter @Setter private Shop shop;
    @Getter @Setter private Long total;
    @Getter @Setter private Long bought;

    
    public ShopListDetails(Shop shop, Long total, Long bought){
        this.shop = shop;
        this.total = total;
        this.bought = bought;
    }
}
