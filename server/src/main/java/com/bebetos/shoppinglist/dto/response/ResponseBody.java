package com.bebetos.shoppinglist.dto.response;

import lombok.Getter;
import lombok.Setter;


public class ResponseBody<T> {

    @Getter @Setter private String message;
    @Getter @Setter private T data;
    

    public ResponseBody(){
    }

    public ResponseBody(String message) {
        this.message = message;
    }

    public ResponseBody(String message, T data){
        this.message = message;
        this.data = data;
    }
        
}