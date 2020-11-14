package com.bebetos.shoppinglist.dto.request;

import lombok.Getter;
import lombok.Setter;

public class NewPasswordRequest {

   @Getter @Setter private String email;
   @Getter @Setter private String oldPassword;
   @Getter @Setter private String newPassword;
    
}