package com.example.paymentservice.service;

import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

@Autowired
    private PaymentRepository repository;


public Payment doPayment(Payment payment){
   payment.setPaymentStatus(paymentProcessing());
    payment.setTransactionId(UUID.randomUUID().toString());
    System.out.println("I'm service 2");

    return repository.save(payment);
}

    public String paymentProcessing(){
        return new Random().nextBoolean()?"success":"fail";

    }

    public Payment findPaymentHistoryByOrderId(int orderId) {

   return  repository.findByOrderId(orderId);
    }
}
