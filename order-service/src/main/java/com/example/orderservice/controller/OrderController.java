package com.example.orderservice.controller;


import com.example.orderservice.dto.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.orderservice.service.OrderServices;

@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
private OrderServices service;

    @PostMapping("/bookOrder")
    public ResponseEntity<?> bookOrder(@RequestBody TransactionRequest request){
        return  service.saveOrder(request);
    }

    @GetMapping("/get-all/{userId}")
    public ResponseEntity<?> getAll(@PathVariable("userId") int userId){
        return  service.findAllByUserId(userId);

    }
    @PutMapping("/update/{orderId}")
    public void update(@PathVariable("orderId") int orderId){
          service.updateOrder(orderId);

    }

}
