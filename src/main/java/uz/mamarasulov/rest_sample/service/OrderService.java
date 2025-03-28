package uz.mamarasulov.rest_sample.service;

import uz.mamarasulov.rest_sample.exception.UpdateStateException;
import uz.mamarasulov.rest_sample.model.Order;
import uz.mamarasulov.rest_sample.web.model.OrderFilter;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public interface OrderService {

    List<Order> filterByOrder(OrderFilter filter);

    List<Order> findAll();

    Order findById(Long id);

    Order save(Order order);

    Order update(Order order);

    void deleteById(Long id);

    void deleteByIdIn(List<Long> ids);

    default void checkForUpdate(Long orderId) {
        Order currentOrder = findById(orderId);
        Instant now = Instant.now();

        Duration duration = Duration.between(currentOrder.getUpdateAt(), now);

        if (duration.getSeconds() > 5) {
            throw new UpdateStateException("Can't update");
        }
    }
}
