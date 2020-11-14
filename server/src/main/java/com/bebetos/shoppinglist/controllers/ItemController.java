package com.bebetos.shoppinglist.controllers;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.bebetos.shoppinglist.dto.request.item.DeleteItemRequest;
import com.bebetos.shoppinglist.dto.request.item.NewItemRequest;
import com.bebetos.shoppinglist.dto.request.item.UpdateItemRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired private CurrentUserService currentUserService;

    @Autowired private ShopRepository shopRepo;
    @Autowired private ItemRepository itemRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger("InfoOperations");


    @GetMapping(value = "/getAll", consumes = "application/json")
    public ResponseEntity<ResponseBody<Shop>> getAll(@RequestParam("shopId") Long shopId){
        ResponseBody<Shop> res = new ResponseBody<>();

        User user = currentUserService.getCurrentUser();
        if (user == null) {
            res.setMessage(Message.USER_NOT_FOUND.getValue());
            return new ResponseEntity<ResponseBody<Shop>>(res, HttpStatus.UNAUTHORIZED);
        }

        Optional<Shop> optShop = shopRepo.findById(shopId);
        if(optShop.isEmpty()){
            res.setMessage(Message.SHOP_NOT_FOUND.getValue());
            return new ResponseEntity<ResponseBody<Shop>>(res, HttpStatus.NOT_FOUND);
        }

        Shop shop = optShop.get();
        List<Item> items = itemRepo.findAllByShop(shop);
        
        shop.setItems(items);

        res.setData(shop);
        return new ResponseEntity<ResponseBody<Shop>>(res, HttpStatus.OK);
    }


    @PostMapping(value = "/save", consumes = "application/json")
    public ResponseEntity<ResponseBody<Item>> addItem(@RequestBody NewItemRequest request) {
        ResponseBody<Item> res = new ResponseBody<>();

        User user = currentUserService.getCurrentUser();
        if (user == null) {
            res.setMessage(Message.USER_NOT_FOUND.getValue());
            return new ResponseEntity<ResponseBody<Item>>(res, HttpStatus.UNAUTHORIZED);
        }

        Optional<Shop> optShop = shopRepo.findById(request.getShopId());
        if(optShop.isEmpty()){
            res.setMessage(Message.SHOP_NOT_FOUND.getValue());
            return new ResponseEntity<ResponseBody<Item>>(res, HttpStatus.NOT_FOUND);
        }

        try {

            Item item = new Item();
            item.setName(request.getName());
            item.setShop(optShop.get());
            item = itemRepo.save(item);

            res.setData(item);
            res.setMessage(Message.ITEM_CREATION_SUCCESS.getValue());
            return new ResponseEntity<ResponseBody<Item>>(res, HttpStatus.OK);
                
        } catch (Exception e) {
            LOGGER.error("[/item/save] Exception: " + e.getMessage());
            LOGGER.error("StackTrace: ", e);
            res.setMessage(Message.ITEM_CREATION_ERROR.getValue());
            return new ResponseEntity<ResponseBody<Item>>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }


    @PostMapping(value = "/update", consumes = "application/json")
    public ResponseEntity<ResponseBody<Item>> updateItem(@Valid @RequestBody UpdateItemRequest request){
        ResponseBody<Item> res = new ResponseBody<>();

        User user = currentUserService.getCurrentUser();
        if (user == null) {
            res.setMessage(Message.USER_NOT_FOUND.getValue());
            return new ResponseEntity<ResponseBody<Item>>(res, HttpStatus.UNAUTHORIZED);
        }

        Optional<Item> optItem = itemRepo.findById(request.getId());
        if(optItem.isEmpty()){
            res.setMessage(Message.ITEM_NOT_FOUND.getValue());
            return new ResponseEntity<ResponseBody<Item>>(res, HttpStatus.NOT_FOUND);
        }

        try {

            Item item = optItem.get();
            item.setIsBought(request.getIsBought());
            item.setUnavailable(request.getUnavailable());
            item = itemRepo.save(item);

            res.setData(item);
            res.setMessage(Message.ITEM_UPDATE_SUCCESS.getValue());
            return new ResponseEntity<ResponseBody<Item>>(res, HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("[/item/update] Exception: " + e.getMessage());
            LOGGER.error("StackTrace: ", e);
            res.setMessage(Message.ITEM_UPDATE_ERROR.getValue());
            return new ResponseEntity<ResponseBody<Item>>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Transactional
    @PostMapping(value = "/delete", consumes = "application/json")
    public ResponseEntity<ResponseBody<String>> deleteItem(@Valid @RequestBody DeleteItemRequest request) {
        ResponseBody<String> res = new ResponseBody<>();

        User user = currentUserService.getCurrentUser();
        if (user == null) {
            res.setMessage(Message.USER_NOT_FOUND.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.UNAUTHORIZED);
        }
        
        try {

            Iterable<Item> items = itemRepo.findAllById(request.getIds());
            itemRepo.deleteAll(items);

            res.setMessage(Message.ITEM_DELETE_SUCCESS.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.OK);        
        } catch (Exception e) {
            LOGGER.error("[/item/delete] Exception: " + e.getMessage());
            LOGGER.error("StackTrace: ", e);
            res.setMessage(Message.ITEM_DELETE_ERROR.getValue());
            return new ResponseEntity<ResponseBody<String>>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    
}
