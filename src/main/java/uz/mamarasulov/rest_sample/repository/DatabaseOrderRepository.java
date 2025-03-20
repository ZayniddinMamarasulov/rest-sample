package uz.mamarasulov.rest_sample.repository;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.mamarasulov.rest_sample.model.Order;

public interface DatabaseOrderRepository extends JpaRepository<Order, Long> {
}
