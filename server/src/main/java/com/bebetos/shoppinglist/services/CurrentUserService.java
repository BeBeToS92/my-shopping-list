package com.bebetos.shoppinglist.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bebetos.shoppinglist.models.User;
import com.bebetos.shoppinglist.repositories.UserRepository;

@Service
public class CurrentUserService {

    @Autowired private UserRepository userRepo;


    public User getCurrentUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ((authentication instanceof AnonymousAuthenticationToken)) {
		    return null;
        }
        
        String email = authentication.getName();
        Optional<User> optUser = userRepo.findByEmail(email);
        if(optUser.isEmpty()){
            return null;
        }
        
        return optUser.get();
    }
    

    public String getCurrentUserName() {
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		return currentUserName;
    }
}
