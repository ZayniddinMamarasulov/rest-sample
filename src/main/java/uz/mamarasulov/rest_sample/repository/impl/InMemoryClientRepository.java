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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class InMemoryClientRepository implements ClientRepository {

    private OrderRepository orderRepository;

    private final Map<Long, Client> repository = new ConcurrentHashMap<>();

    private final AtomicLong currentId = new AtomicLong(1);

    @Override
    public List<Client> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<Client> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Client save(Client client) {
        Long clientId = currentId.getAndIncrement();
        client.setId(clientId);
        repository.put(clientId, client);
        return client;
    }

    @Override
    public Client update(Client client) {
        Long clientId = client.getId();
        Client currentClient = repository.get(clientId);
        if (currentClient == null) {
            throw new EntityNotFoundException(MessageFormat.format("Client not found ID:{0}", clientId));
        }

        BeanUtils.copyNonNullProperties(client, currentClient);
        currentClient.setId(clientId);

        repository.put(clientId, currentClient);
        return currentClient;
    }

    @Override
    public void deleteById(Long id) {
        Client client = repository.get(id);
        if (client == null) {
            throw new EntityNotFoundException(MessageFormat.format("Client not found ID:{0}", id));
        }

        orderRepository.deleteByIdIn(client.getOrders().stream().map(Order::getId).collect(Collectors.toList()));
        repository.remove(id);
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
