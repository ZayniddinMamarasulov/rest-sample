package uz.mamarasulov.rest_sample.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.mamarasulov.rest_sample.model.Client;

import java.util.List;

public interface DatabaseClientRepository extends JpaRepository<Client, Long> {

    @Override
    @EntityGraph(attributePaths = {"orders"})
    List<Client> findAll();
}
