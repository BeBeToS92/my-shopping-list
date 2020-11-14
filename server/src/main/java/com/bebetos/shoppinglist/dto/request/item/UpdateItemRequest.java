package com.bebetos.shoppinglist.dto.request.item;

import lombok.Getter;
import lombok.Setter;

public class UpdateItemRequest {

    @Getter @Setter private Long id;
    @Getter @Setter private Integer isBought;
    @Getter @Setter private Integer unavailable;
    
}
