package com.bebetos.shoppinglist.dto.request.item;

import lombok.Getter;
import lombok.Setter;

public class NewItemRequest {

    @Getter @Setter private String name;
    @Getter @Setter private Long shopId;
    
}
