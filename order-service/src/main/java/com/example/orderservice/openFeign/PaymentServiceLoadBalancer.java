package com.example.orderservice.openFeign;


import feign.Feign;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;

@LoadBalancerClient(name="PAYMENT-SERVICE")
public class PaymentServiceLoadBalancer {

    @LoadBalanced
    @Bean
public Feign.Builder feBuilder(){
    return  Feign.builder();
}
}
