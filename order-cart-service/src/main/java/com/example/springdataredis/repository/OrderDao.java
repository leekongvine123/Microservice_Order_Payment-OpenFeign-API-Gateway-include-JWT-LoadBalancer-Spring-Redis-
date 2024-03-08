package com.example.springdataredis.repository;


import com.example.springdataredis.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDao {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate template;
public static final String HASH_KEY = "Order";
public Order save(Order order){

    template.opsForHash().put(HASH_KEY, order.getId(), order);
    return order;
}


public List<Order> findAll(){
    return template.opsForHash().values(HASH_KEY);
}

public Order findProductById(int id){
    return (Order) template.opsForHash().get(HASH_KEY,id);
}




public String deleteProduct(int id){
//    Order order =(Order)template.opsForHash().get(HASH_KEY,id);
//    if(order.getStatus().equals("Processing")){
        template.opsForHash().delete(HASH_KEY,id);

//    }
return "product removed";
}
}
