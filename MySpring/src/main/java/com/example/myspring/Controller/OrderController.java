package com.example.myspring.Controller;

import com.example.myspring.Dto.CreateOrderRequest;
import com.example.myspring.Model.Order;
import com.example.myspring.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<Order> createOrder(@PathVariable Integer userId,
                                             @RequestBody @Valid CreateOrderRequest createOrderRequest) {
        Order order = orderService.createOrder(userId, createOrderRequest.getBuyItemList());
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
