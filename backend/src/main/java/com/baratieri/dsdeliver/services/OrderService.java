package com.baratieri.dsdeliver.services;

import com.baratieri.dsdeliver.dto.OrderDTO;
import com.baratieri.dsdeliver.dto.ProductDTO;
import com.baratieri.dsdeliver.entities.Order;
import com.baratieri.dsdeliver.entities.Product;
import com.baratieri.dsdeliver.enums.OrderStatus;
import com.baratieri.dsdeliver.repositories.OrderRepository;
import com.baratieri.dsdeliver.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<OrderDTO> findAll() {
        List<Order> list = repository.findOrdersWithProducts();
        return list.stream().map(x -> new OrderDTO(x)).collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {
        Order order = new Order(null, dto.getAddress(), dto.getLatitude(), dto.getLongitude(), Instant.now(),
                OrderStatus.PENDING, dto.getTotal());
        for (ProductDTO p : dto.getProducts()) {
            Product product = productRepository.getById(p.getId());
            order.getProducts().add(product);
        }

        order = repository.save(order);
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO setDelivered(Long id) {
        Order order = repository.getById(id);
        order.setStatus(OrderStatus.DELIVERED);
        order = repository.save(order);
        return new OrderDTO(order);
    }
}
