package uz.mamarasulov.rest_sample.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import uz.mamarasulov.rest_sample.exception.EntityNotFoundException;
import uz.mamarasulov.rest_sample.model.Client;
import uz.mamarasulov.rest_sample.model.Order;
import uz.mamarasulov.rest_sample.repository.ClientRepository;
import uz.mamarasulov.rest_sample.repository.OrderRepository;
import uz.mamarasulov.rest_sample.utils.BeanUtils;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryOrderRepository implements OrderRepository {

    private ClientRepository clientRepository;

    private final Map<Long, Order> repository = new ConcurrentHashMap<>();

    private final AtomicLong currentId = new AtomicLong(1);

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Order save(Order order) {
        Long orderId = currentId.getAndIncrement();
        Long clientId = order.getClient().getId();
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        order.setClient(client);
        order.setId(orderId);
        Instant now = Instant.now();
        order.setCreateAt(now);
        order.setUpdateAt(now);

        repository.put(orderId, order);

        client.addOrder(order);

        clientRepository.update(client);

        return order;
    }

    @Override
    public Order update(Order order) {
        Long orderId = order.getId();
        Instant now = Instant.now();
        Order currentOrder = repository.get(orderId);

        if (currentOrder == null) {
            throw new EntityNotFoundException(MessageFormat.format("Order not found ID {0}", orderId));
        }

        BeanUtils.copyNonNullProperties(order, currentOrder);
        currentOrder.setUpdateAt(now);
        currentOrder.setId(orderId);

        repository.put(orderId, currentOrder);

        return currentOrder;
    }

    @Override
    public void deleteById(Long id) {
        repository.remove(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        ids.forEach(repository::remove);
    }

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
}
