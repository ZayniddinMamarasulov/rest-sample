package uz.mamarasulov.rest_sample.service.impl;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mamarasulov.rest_sample.exception.EntityNotFoundException;
import uz.mamarasulov.rest_sample.model.Client;
import uz.mamarasulov.rest_sample.model.Order;
import uz.mamarasulov.rest_sample.repository.DatabaseClientRepository;
import uz.mamarasulov.rest_sample.repository.DatabaseOrderRepository;
import uz.mamarasulov.rest_sample.repository.OrderRepository;
import uz.mamarasulov.rest_sample.service.ClientService;
import uz.mamarasulov.rest_sample.utils.BeanUtils;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseClientService implements ClientService {

    private final DatabaseClientRepository clientRepository;

    private final DatabaseOrderRepository orderRepository;

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Client with ID:{0}", id))
                );
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        Client existedClient = findById(client.getId());

        BeanUtils.copyNonNullProperties(client, existedClient);

        return clientRepository.save(client);
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Client saveWithOrders(Client client, List<Order> orders) {
        Client savedClient = clientRepository.save(client);

        if (true) throw new RuntimeException();

        for (Order order : orders) {
            order.setClient(savedClient);
            var savedOrder = orderRepository.save(order);
            savedClient.addOrder(savedOrder);
        }

        return savedClient;
    }
}
