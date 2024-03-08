package com.example.cloudgatewayop.OpenFeign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@FeignClient(name = "IDENTITY-SERVICE",path = "/auth")
public interface IdentityClient {


     @GetMapping(value = "validate")
     String validate(@RequestParam("token") String token);

}
