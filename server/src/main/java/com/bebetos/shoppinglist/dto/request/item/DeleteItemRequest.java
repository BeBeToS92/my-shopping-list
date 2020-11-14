package com.bebetos.shoppinglist.dto.request.item;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class DeleteItemRequest {

    @Getter @Setter private List<Long> ids;
    
}
