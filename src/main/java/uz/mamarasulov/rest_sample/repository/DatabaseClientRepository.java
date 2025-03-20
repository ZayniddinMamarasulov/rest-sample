package uz.mamarasulov.rest_sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mamarasulov.rest_sample.model.Client;

public interface DatabaseClientRepository extends JpaRepository<Client, Long> {
}
