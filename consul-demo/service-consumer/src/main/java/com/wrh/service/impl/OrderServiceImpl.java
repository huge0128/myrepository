package com.wrh.service.impl;

import com.wrh.pojo.Order;
import com.wrh.pojo.Transaction;
import com.wrh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Order selectOrderById(Integer id) {
        return new Order(id, "order-001", "beijing", 888.00, selectTransactionListByLoadBalancerAnnotation());
    }

    private List<Transaction> selectTransactionListByLoadBalancerAnnotation() {
        // ResponseEntity: 封装返回值
        ResponseEntity<List<Transaction>> response = restTemplate.exchange(
                "http://service-provider/tx/list",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Transaction>>() {

                }
        );
        return response.getBody();
    }

}
