package com.example.springdataredis.repository;


import com.example.springdataredis.OpenFeign.OrderClient;
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


    @Autowired
    OrderClient orderClient;
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


public Boolean updateOrderById(int id){
    try {
        Order order = findProductById(id);
        order.setStatus("Paid");
        template.opsForHash().put(HASH_KEY,id, order);
       orderClient.update(id);
        return true;
    }catch (Exception e){
        return false;
    }


}

public String deleteProduct(int id){
    Order order = findProductById(id);
    if(!order.getStatus().equals("Paid")){
        template.opsForHash().delete(HASH_KEY,id);
        return "product removed";
    }else{
        return "product removed failed";

    }

}

    public String deleteProduct2(int id){

            template.opsForHash().delete(HASH_KEY,id);
            return "product removed";

    }
}
