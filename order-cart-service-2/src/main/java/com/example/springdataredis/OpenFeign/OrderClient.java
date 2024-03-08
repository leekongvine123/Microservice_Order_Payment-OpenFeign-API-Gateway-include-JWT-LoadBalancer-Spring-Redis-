package com.example.springdataredis.OpenFeign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ORDER-SERVICE",path = "/order")
public interface OrderClient {

    @DeleteMapping("/{id}")
    String remove(@PathVariable int id);

    @PutMapping("/update/{orderId}")
    void update(@PathVariable("orderId") int orderId);
}
