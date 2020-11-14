package com.bebetos.shoppinglist.dto.request;


import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

public class UserRequest {

    @NotBlank @Getter @Setter private String email;
    @NotBlank @Getter @Setter private String password;

}