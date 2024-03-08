package com.example.orderservice.openFeign;


import com.example.orderservice.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ORDER-CART-SERVICE",path = "/order-cart")
public interface OrderCartClient {

    @DeleteMapping("/{id}")
    String remove(@PathVariable int id);

    @PostMapping()
    Order save(@RequestBody Order order);
}
