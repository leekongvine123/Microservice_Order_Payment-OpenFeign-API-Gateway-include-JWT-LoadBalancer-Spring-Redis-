package com.example.springdataredis.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Order")
public class Order implements Serializable {


    @Id
    private int id;
    private int userId;
    private int qty;
    private double price;
    private String status;



}
