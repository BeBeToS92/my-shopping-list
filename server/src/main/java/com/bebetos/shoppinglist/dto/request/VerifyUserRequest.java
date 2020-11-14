package com.bebetos.shoppinglist.dto.request;

import lombok.Getter;
import lombok.Setter;


public class VerifyUserRequest {

    @Getter @Setter private String email;
    @Getter @Setter private String token;
    
}