package uz.mamarasulov.rest_sample.service;

import uz.mamarasulov.rest_sample.model.Client;

import java.util.List;

public interface ClientService {

    List<Client> findAll();

    Client findById(Long id);

    Client save(Client client);

    Client update(Client client);

    void deleteById(Long id);
}
