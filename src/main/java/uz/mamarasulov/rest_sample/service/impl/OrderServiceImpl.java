package uz.mamarasulov.rest_sample.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mamarasulov.rest_sample.exception.EntityNotFoundException;
import uz.mamarasulov.rest_sample.exception.UpdateStateException;
import uz.mamarasulov.rest_sample.model.Order;
import uz.mamarasulov.rest_sample.repository.OrderRepository;
import uz.mamarasulov.rest_sample.service.OrderService;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found with this ID"));
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        checkForUpdate(order.getId());
        return orderRepository.update(order);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        orderRepository.deleteByIdIn(ids);
    }
}
