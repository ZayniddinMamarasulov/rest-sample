package uz.mamarasulov.rest_sample.mapper.v1;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.mamarasulov.rest_sample.model.Order;
import uz.mamarasulov.rest_sample.service.ClientService;
import uz.mamarasulov.rest_sample.web.model.ClientResponse;
import uz.mamarasulov.rest_sample.web.model.OrderListResponse;
import uz.mamarasulov.rest_sample.web.model.OrderResponse;
import uz.mamarasulov.rest_sample.web.model.UpsertOrderRequest;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ClientService clientServiceImpl;

    public Order requestToOrder(UpsertOrderRequest request) {
        Order order = new Order();

        order.setCost(request.getCost());
        order.setProduct(request.getProduct());
        order.setClient(clientServiceImpl.findById(request.getClientId()));

        return order;
    }

    public Order requestToOrder(Long orderId, UpsertOrderRequest request) {
        Order order = requestToOrder(request);
        order.setId(orderId);

        return order;
    }

    public OrderResponse orderToResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setId(order.getId());
        orderResponse.setCost(order.getCost());
        orderResponse.setProduct(order.getProduct());

        return orderResponse;
    }

    public List<OrderResponse> orderListToResponseList(List<Order> orders) {
        return orders.stream()
                .map(this::orderToResponse)
                .collect(Collectors.toList());
    }

    public OrderListResponse orderListToOrderListResponse(List<Order> orders) {
        OrderListResponse response = new OrderListResponse();
        response.setOrders(orderListToResponseList(orders));

        return response;
    }
}
