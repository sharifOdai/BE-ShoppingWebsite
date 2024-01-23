package com.userLogin.controller;

import com.userLogin.model.Item;
import com.userLogin.model.Order;
import com.userLogin.model.User;
import com.userLogin.model.UserRequest;
import com.userLogin.service.UserService;
import com.userLogin.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/public/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserServiceImpl userServiceImpl;


    @PostMapping("/sign-up")
    @CrossOrigin
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest){
        try{
            userService.createUser(userRequest);
            return ResponseEntity.ok("User Signed-up Successfully.");
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
    @PutMapping("/update")
    @CrossOrigin
    public ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest){
        try {
            userService.updateUser(userRequest.toCustomUser());
            return ResponseEntity.ok("User Updated Successfully.");
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
    @GetMapping("/get/{id}")
    @CrossOrigin
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @CrossOrigin
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok("User Deleted Successfully.");
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PostMapping("/add-favorite/{userId}/{itemId}")
    @CrossOrigin
    public ResponseEntity<?> addFavoriteItem(@PathVariable Long userId, @PathVariable Long itemId) {
        try {
            userService.addFavoriteItem(userId, itemId);
            return ResponseEntity.ok("Item added to favorite list successfully.");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add item to favorite list.");
        }
    }

    @GetMapping("/favorite-items/{userId}")
    @CrossOrigin
    public ResponseEntity<?> getFavoriteItems(@PathVariable Long userId) {
        try {
            List<Item> favoriteItems = userService.getFavoriteItems(userId);
            return ResponseEntity.ok(favoriteItems);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve favorite items.");
        }
    }

    @DeleteMapping("/remove-favorite/{userId}/{itemId}")
    @CrossOrigin
    public ResponseEntity<?> removeItemFromFavorites(@PathVariable Long userId, @PathVariable Long itemId) {
        try {
            userService.removeItemFromFavorites(userId, itemId);
            return ResponseEntity.ok("Item removed from favorite list successfully.");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to remove item from favorite list.");
        }
    }

    @GetMapping("/getOrders/{userId}")
    @CrossOrigin
    public ResponseEntity<?> getOrders(@PathVariable Long userId) {
        try {
            List<Order> orders = userService.getOrders(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }



    @PostMapping("/add-item-to-order/{userId}/{itemId}")
    @CrossOrigin
    public ResponseEntity<?> addItemToUserOrder(@PathVariable Long userId, @PathVariable Long itemId) throws Exception {

        try {
            userService.addItemToUserOrder(itemId, userId);
            return ResponseEntity.ok("Item added to Order user item-list. Item ID: " + itemId);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @DeleteMapping("/remove-item-from-order/{orderId}/{itemId}/{userId}")
    @CrossOrigin
    public ResponseEntity<?> removeItemFromUserOrder(@PathVariable Long orderId, @PathVariable Long itemId, @PathVariable Long userId ){
        try {
            userService.removeItemFromUserOrder(orderId, itemId, userId);
            return ResponseEntity.ok("Item removed from temp order: " + itemId);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/order-items/{userId}/{orderId}")
    @CrossOrigin
    public ResponseEntity<?> getOrderItemListByUserId(@PathVariable Long userId, @PathVariable Long orderId) {
        return null;
    }


    @GetMapping("/getClosedOrdersByUserId/{userId}")
    @CrossOrigin
    public List<Order> getClosedOrdersByUserId(@PathVariable Long userId){
        return userService.getClosedOrdersByUserId(userId);
    }

    @GetMapping("/getTempOrdersByUserId/{userId}")
    @CrossOrigin
    public List<Order> getTempOrdersByUserId(@PathVariable Long userId){
        return userService.getTempOrdersByUserId(userId);
    }
}



