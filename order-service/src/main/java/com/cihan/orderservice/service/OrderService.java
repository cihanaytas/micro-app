package com.cihan.orderservice.service;

import com.cihan.orderservice.dto.OrderDto;
import com.cihan.orderservice.model.Order;
import com.cihan.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void save(Order order){
        orderRepository.save(order);
    }
}
