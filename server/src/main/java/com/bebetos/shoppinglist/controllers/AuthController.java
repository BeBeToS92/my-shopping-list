package com.bebetos.shoppinglist.controllers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bebetos.shoppinglist.repositories.RecoverAccountRepository;
import com.bebetos.shoppinglist.repositories.UserRepository;
import com.bebetos.shoppinglist.repositories.RoleRepository;
import com.bebetos.shoppinglist.services.RandomGenerator;
import com.bebetos.shoppinglist.dto.request.RecoverPasswordRequest;
import com.bebetos.shoppinglist.dto.request.UserRequest;
import com.bebetos.shoppinglist.dto.request.VerifyUserRequest;
import com.bebetos.shoppinglist.dto.response.Message;
import com.bebetos.shoppinglist.dto.response.ResponseBody;
import com.bebetos.shoppinglist.models.RecoverAccount;
import com.bebetos.shoppinglist.models.Role;
import com.bebetos.shoppinglist.models.User;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RecoverAccountRepository recoverAccountRepo;
    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private RandomGenerator rdgService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger("InfoOperations");

    @Transactional
    @PostMapping(path = "/register", consumes = "application/json")
    public ResponseEntity<ResponseBody<String>> register(@Valid @RequestBody UserRequest userRequest) {
        ResponseBody<String> res = new ResponseBody<>();

        Optional<User> optUser = userRepo.findByEmail(userRequest.getEmail());
        if (optUser.isPresent()) {
            res.setMessage(Message.USER_ALREADY_REGISTERED.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.BAD_REQUEST);
        }

        try {
            User user = new User();
            Role role = roleRepo.findByName("ROLE_USER");

            user.setEmail(userRequest.getEmail());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            user.setEnabled(true);
            user.setRoles(Arrays.asList(role));

            userRepo.save(user);

            /*
            // TODO: Add service per email e verifica
            String token = rdgService.getRecoverCode(6);
            RecoverAccount recoverAccount = new RecoverAccount();
            recoverAccount.setEmail(userRequest.getEmail());
            recoverAccount.setToken(token);
            recoverAccount.setValidUntil(LocalDateTime.now().plusHours(24));

            recoverAccountRepo.save(recoverAccount);
            */

            res.setMessage(Message.USER_CREATION_SUCCESS.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("[/user/register] Exception: " + e.getMessage());
            LOGGER.error("Stacktrace: ", e);
            res.setMessage(Message.USER_CREATION_ERROR.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping(value = "/verify", consumes = "application/json")
    public ResponseEntity<ResponseBody<String>> verifyUser(@Valid @RequestBody VerifyUserRequest vuRequest) {
        ResponseBody<String> res = new ResponseBody<>();

        Optional<User> optUser = userRepo.findByEmail(vuRequest.getEmail());
        if (optUser.isEmpty()) {
            res.setMessage(Message.USER_NOT_FOUND.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.BAD_REQUEST);
        }

        Optional<RecoverAccount> optRecoverAccount = recoverAccountRepo.findByEmailAndToken(vuRequest.getEmail(),
                vuRequest.getToken());
        if (optRecoverAccount.isEmpty()) {
            res.setMessage(Message.PASSWORD_CHANGE_ERROR.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.BAD_REQUEST);
        }

        try {
            // TODO: Complete procedure

            res.setMessage(Message.PASSWORD_CHANGE_ERROR.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            LOGGER.error("[/user/verify] Exception: " + e.getMessage());
            LOGGER.error("StackTrace: ", e);
            res.setMessage(Message.USER_CREATION_ERROR.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(value = "/sendRecoverEmail", consumes = "application/json")
    public ResponseEntity<ResponseBody<String>> sendRecoverEmail(@Valid @RequestBody String email) {
        ResponseBody<String> res = new ResponseBody<>();

        Optional<User> optUser = userRepo.findByEmail(email);
        if (optUser.isEmpty()) {
            res.setMessage(Message.USER_ALREADY_REGISTERED.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.OK);
        }

        try {

            String token = rdgService.getRecoverCode(6);

            RecoverAccount recAccount = new RecoverAccount();
            recAccount.setEmail(email);
            recAccount.setToken(token);
            recAccount.setValidUntil(LocalDateTime.now().plusHours(24));
            recoverAccountRepo.save(recAccount);

            // TODO: Aggiungere service di invio email
            res.setMessage(Message.PASSWORD_RECOVER_SUCCESS.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.OK);


        } catch (Exception e) {
            LOGGER.error("[/user/sendRecoverEmail] Exception: " + e.getMessage());
            LOGGER.error("StackTrace: ", e);
            res.setMessage(Message.PASSWORD_RECOVER_ERROR.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(value = "/recoverPassword", consumes = "application/json")
    public ResponseEntity<ResponseBody<String>> recoverPassword(RecoverPasswordRequest request) {
        ResponseBody<String> res = new ResponseBody<>();

        Optional<User> optUser = userRepo.findByEmail(request.getEmail());
        if (optUser.isEmpty()) {
            res.setMessage(Message.USER_NOT_FOUND.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.NOT_FOUND);
        }

        Optional<RecoverAccount> optRecAccount = recoverAccountRepo.findByEmailAndToken(request.getEmail(),
                request.getToken());
        if (optRecAccount.isEmpty()) {
            res.setMessage(Message.PASSWORD_CHANGE_ERROR.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.NOT_FOUND);
        }

        try {

            RecoverAccount recAccount = optRecAccount.get();

            if (!recAccount.getToken().equals(request.getToken())) {
                res.setMessage(Message.PASSWORD_CHANGE_ERROR.getValue());
                return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.NOT_FOUND);
            }

            User user = optUser.get();
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepo.save(user);

            res.setMessage(Message.PASSWORD_CHANGE_SUCCESS.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("[/user/recoverPassword] Exception: " + e.getMessage());
            LOGGER.error("StackTrace: ", e);
            res.setMessage(Message.PASSWORD_RECOVER_ERROR.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.INTERNAL_SERVER_ERROR);        }

    }

}