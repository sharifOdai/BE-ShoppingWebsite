package com.userLogin.controller;

import com.userLogin.model.Item;
import com.userLogin.model.Order;
import com.userLogin.model.User;
import com.userLogin.service.OrderService;
import com.userLogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("api/public/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        try {
            orderService.createOrder(order);
            return ResponseEntity.ok("order created successfully." + order);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }



    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable Long orderId) {
       return orderService.getOrderById(orderId);

    }



    @PostMapping("/processPayment/{userId}/{orderId}")
    @CrossOrigin
    public ResponseEntity<String> processPayment(@PathVariable Long userId, @PathVariable Long orderId) {
        try {
            orderService.processPayment(userId,orderId);
            return ResponseEntity.ok("Order submitted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
