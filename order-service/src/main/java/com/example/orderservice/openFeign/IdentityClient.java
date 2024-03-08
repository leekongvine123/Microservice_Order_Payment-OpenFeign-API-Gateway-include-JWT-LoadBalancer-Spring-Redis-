package com.example.orderservice.openFeign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "IDENTITY-SERVICE",path = "/auth")
public interface IdentityClient {


     @GetMapping(value = "findById/{id}")
      Boolean findById(@PathVariable("id") int id);

}
