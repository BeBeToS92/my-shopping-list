package com.bebetos.shoppinglist.controllers;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bebetos.shoppinglist.repositories.UserRepository;
import com.bebetos.shoppinglist.services.CurrentUserService;
import com.bebetos.shoppinglist.dto.request.NewPasswordRequest;
import com.bebetos.shoppinglist.dto.response.Message;
import com.bebetos.shoppinglist.dto.response.ResponseBody;
import com.bebetos.shoppinglist.models.User;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired private UserRepository userRepo;

    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private CurrentUserService currentUserService;

    private static final Logger LOGGER = LoggerFactory.getLogger("InfoOperations");

    @PostMapping(value = "/changePassword", consumes = "application/json")
    public ResponseEntity<ResponseBody<String>> changePassword(@Valid @RequestBody NewPasswordRequest request) {
        ResponseBody<String> res = new ResponseBody<>();

        User user = currentUserService.getCurrentUser();
        if (user == null) {
            res.setMessage(Message.USER_NOT_FOUND.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.UNAUTHORIZED);
        }

        try {
            String currentPassword = user.getPassword();

            if (!passwordEncoder.matches(request.getOldPassword(), currentPassword)) {
                res.setMessage(Message.USER_WRONG_PASSWORD.getValue());
                return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.BAD_REQUEST);
            }

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            user.setUpdatedAt(LocalDateTime.now());
            userRepo.save(user);

            res.setMessage(Message.PASSWORD_CHANGE_SUCCESS.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("[/user/changePassword] Exception: " + e.getMessage());
            LOGGER.error("StackTrace: ", e);
            res.setMessage(Message.PASSWORD_CHANGE_ERROR.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.INTERNAL_SERVER_ERROR);  
        }


    }



}