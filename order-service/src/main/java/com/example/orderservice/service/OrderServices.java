package com.example.orderservice.service;


import com.example.orderservice.dto.ErrorResponse;
import com.example.orderservice.dto.Payment;
import com.example.orderservice.dto.TransactionRequest;
import com.example.orderservice.dto.TransactionResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.openFeign.IdentityClient;
import com.example.orderservice.openFeign.OrderCartClient;
import com.example.orderservice.openFeign.PaymentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class OrderServices {

    @Autowired
    private OrderRepository repository;

    @Autowired
    OrderCartClient orderCartClient;

    @Autowired
    PaymentClient paymentClient;


    @Autowired
    IdentityClient identityClient;
    public ResponseEntity<?> saveOrder(TransactionRequest request){
String response ="";


        Order order = request.getOrder();
        if(identityClient.findById(order.getUserId())){
            repository.save(order);
        }else{
            ErrorResponse errorResponse = new ErrorResponse("User Not Found","500","NOT FOUND");
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);


        }

        Payment payment = request.getPayment();

        payment.setAmount(order.getPrice());
        payment.setOrderId(order.getId());

        Payment paymentResponse =    paymentClient.doPayment(payment);
//    template.postForObject("http://PAYMENT-SERVICE/payment/doPayment",payment,Payment.class);

       if(paymentResponse.getPaymentStatus().equals("success")){
           order.setStatus("Paid");
           repository.save(order);
           response ="Payment processing successful and order placed";
       }else{
           order.setStatus("Processing");
           orderCartClient.save(order);
           scheduleOrderCleanup(order.getId());
           response ="there is a failure in payment api, order added to cart";

       }

return ResponseEntity.status(HttpStatus.CREATED).body(new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(),response));
    }
public void updateOrder(int id){
        Order order = repository.findById(id).get();
        order.setStatus("Paid");
        repository.save(order);
}
    public void scheduleOrderCleanup(int orderId) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                orderCartClient.remove(orderId);
                timer.cancel();
            }
        }, 15000);
    }

    public ResponseEntity<?> findAllByUserId(int userId) {

        List<Order> orderList = repository.findAllByUserId(userId);
return ResponseEntity.status(HttpStatus.OK).body(orderList);
    }
}
