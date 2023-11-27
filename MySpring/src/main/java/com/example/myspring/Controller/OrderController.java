package com.example.myspring.Controller;

import com.example.myspring.Dto.CreateOrderRequest;
import com.example.myspring.Model.Order;
import com.example.myspring.Service.OrderService;
import com.example.myspring.Util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(@PathVariable Integer userId,
                                          @RequestParam(defaultValue = "5") @Max(100) @Min(0) Integer limit,
                                          @RequestParam(defaultValue = "0") @Min(0) Integer offset) {
        Page<Order> page = orderService.getOrders(userId, limit, offset);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }
}
