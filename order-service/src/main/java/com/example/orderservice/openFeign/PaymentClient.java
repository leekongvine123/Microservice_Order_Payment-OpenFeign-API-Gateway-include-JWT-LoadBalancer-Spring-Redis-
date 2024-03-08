package com.example.orderservice.openFeign;


import com.example.orderservice.dto.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE",path = "/payment")
public interface PaymentClient {
    @PostMapping("/doPayment")
     Payment doPayment(@RequestBody Payment payment);

    @GetMapping("/{orderId}")
    Payment findPaymentHistoryByOrderId(@PathVariable int orderId);

}
