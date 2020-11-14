package com.bebetos.shoppinglist.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.bebetos.shoppinglist.dto.request.shop.DeleteShopRequest;
import com.bebetos.shoppinglist.dto.request.shop.NewShopRequest;
import com.bebetos.shoppinglist.dto.response.Message;
import com.bebetos.shoppinglist.dto.response.ResponseBody;
import com.bebetos.shoppinglist.models.Item;
import com.bebetos.shoppinglist.models.Shop;
import com.bebetos.shoppinglist.models.User;
import com.bebetos.shoppinglist.repositories.ItemRepository;
import com.bebetos.shoppinglist.repositories.ShopRepository;
import com.bebetos.shoppinglist.services.CurrentUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired private CurrentUserService currentUserService;

    @Autowired private ShopRepository shopRepo;
    @Autowired private ItemRepository itemRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger("InfoOperations");


    @GetMapping(value = "/getAll", consumes = "application/json")
    public ResponseEntity<ResponseBody<List<Shop>>> getAll(){
        ResponseBody<List<Shop>> res = new ResponseBody<>();

        User user = currentUserService.getCurrentUser();
        if (user == null) {
            res.setMessage(Message.USER_NOT_FOUND.getValue());
            return new ResponseEntity<ResponseBody<List<Shop>>>(res, HttpStatus.UNAUTHORIZED);
        }

            
        List<Object[]> result = shopRepo.findAllByUser(user);
        List<Shop> shops = new ArrayList<>();
        for (Object[] object : result) {
            Shop shop = (Shop) object[0];
            shop.setTotal( (Long) object[1]);
            shop.setBought( (Long) object[2]);
            shops.add(shop);
        }
        
        res.setData(shops);
        return new ResponseEntity<ResponseBody<List<Shop>>>(res, HttpStatus.OK);
    }


    @PostMapping(value = "/save", consumes = "application/json")
    public ResponseEntity<ResponseBody<Shop>> addShop(@Valid @RequestBody NewShopRequest request) {
        ResponseBody<Shop> res = new ResponseBody<>();

        User user = currentUserService.getCurrentUser();
        if (user == null) {
            res.setMessage(Message.USER_NOT_FOUND.getValue());
            return new ResponseEntity<ResponseBody<Shop>>(res, HttpStatus.UNAUTHORIZED);
        }

        try {

            Shop shop = new Shop();
            shop.setName(request.getName());
            shop.setUser(user);
            
            shop = shopRepo.save(shop);

            res.setData(shop);
            return new ResponseEntity<ResponseBody<Shop>>(res, HttpStatus.OK);
        
        } catch (Exception e) {
            LOGGER.error("[/shop/save] Exception: " + e.getMessage());
            LOGGER.error("StackTrace: ", e);
            res.setMessage(Message.SHOP_CREATION_ERROR.getValue());
            return new ResponseEntity<ResponseBody<Shop>>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }


    @Transactional
    @PostMapping(value = "/delete", consumes = "application/json")
    public ResponseEntity<ResponseBody<String>> deleteShop(@Valid @RequestBody DeleteShopRequest request) {
        ResponseBody<String> res = new ResponseBody<>();

        User user = currentUserService.getCurrentUser();
        if (user == null) {
            res.setMessage(Message.USER_NOT_FOUND.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.UNAUTHORIZED);
        }

        Optional<Shop> optShop = shopRepo.findById(request.getId());
        if (optShop.isEmpty()) {
            res.setMessage(Message.SHOP_NOT_FOUND.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.NOT_FOUND);
        }


        try {

            Shop shop = optShop.get();
            List<Item> items = itemRepo.findAllByShop(shop);

            itemRepo.deleteAll(items);
            shopRepo.delete(shop);

            res.setMessage(Message.SHOP_DELETE_SUCCESS.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("[/shop/delete] Exception: " + e.getMessage());
            LOGGER.error("StackTrace: ", e);
            res.setMessage(Message.SHOP_DELETE_ERROR.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.INTERNAL_SERVER_ERROR);        
        }
        
    }

    
}
