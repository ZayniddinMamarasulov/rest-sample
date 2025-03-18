package uz.mamarasulov.rest_sample.repository;

import uz.mamarasulov.rest_sample.model.Client;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ClientRepository {

    List<Client> findAll();

    Optional<Client> findById(Long id);

    Client save(Client client);

    Client update(Client client);

    void deleteById(Long id);
}
