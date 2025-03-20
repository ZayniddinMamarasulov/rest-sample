package uz.mamarasulov.rest_sample.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mamarasulov.rest_sample.exception.EntityNotFoundException;
import uz.mamarasulov.rest_sample.model.Client;
import uz.mamarasulov.rest_sample.model.Order;
import uz.mamarasulov.rest_sample.repository.DatabaseOrderRepository;
import uz.mamarasulov.rest_sample.service.ClientService;
import uz.mamarasulov.rest_sample.service.OrderService;
import uz.mamarasulov.rest_sample.utils.BeanUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseOrderService implements OrderService {

    private final DatabaseOrderRepository orderRepository;

    private final ClientService databaseClientService;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The order not found " + id));
    }

    @Override
    public Order save(Order order) {
        Client client = databaseClientService.findById(order.getClient().getId());
        order.setClient(client);
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        checkForUpdate(order.getId());
        Client client = databaseClientService.findById(order.getClient().getId());
        Order existedOrder = findById(order.getId());

        BeanUtils.copyNonNullProperties(order, existedOrder);
        existedOrder.setClient(client);

        return orderRepository.save(existedOrder);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        orderRepository.deleteAllById(ids);
    }
}
