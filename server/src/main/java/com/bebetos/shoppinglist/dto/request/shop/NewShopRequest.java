package com.bebetos.shoppinglist.dto.request.shop;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

public class NewShopRequest {

    @NotBlank @Getter @Setter private String name;

}
