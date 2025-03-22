package uz.mamarasulov.rest_sample.mapper.v2;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uz.mamarasulov.rest_sample.model.Order;
import uz.mamarasulov.rest_sample.web.model.OrderListResponse;
import uz.mamarasulov.rest_sample.web.model.OrderResponse;
import uz.mamarasulov.rest_sample.web.model.UpsertOrderRequest;

import java.util.List;

@DecoratedWith(OrderMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapperV2 {

    Order requestToOrder(UpsertOrderRequest request);

    @Mapping(source = "orderId", target = "id")
    Order requestToOrder(Long orderId, UpsertOrderRequest request);

    OrderResponse orderToResponse(Order order);

    List<OrderResponse> orderListToResponseList(List<Order> orders);

    default OrderListResponse orderListToOrderListResponse(List<Order> orders) {
        OrderListResponse response = new OrderListResponse();
        response.setOrders(orderListToResponseList(orders));

        return response;
    }
}
