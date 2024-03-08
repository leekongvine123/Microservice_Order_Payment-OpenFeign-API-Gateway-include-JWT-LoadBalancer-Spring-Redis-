package com.example.springdataredis.controller;


import com.example.springdataredis.entity.Order;
import com.example.springdataredis.repository.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-cart")
public class OrderController {

    @Autowired
    private OrderDao dao;


    @PostMapping()
    public Order save(@RequestBody Order order){
        return dao.save(order);
    }



    @GetMapping()
    public List<Order> getAllProducts(){
        return dao.findAll();
    }


    @GetMapping("/{id}")
    public Order findProduct(@PathVariable int id){
        return dao.findProductById(id);
    }


    @DeleteMapping("/{id}")
    public String remove(@PathVariable int id){
return dao.deleteProduct(id);
    }

    @DeleteMapping("/version-2/{id}")
    public String remove2(@PathVariable int id){
        return dao.deleteProduct2(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id){
         dao.updateOrderById(id);
    }


}
