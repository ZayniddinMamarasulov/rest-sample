package uz.mamarasulov.rest_sample.mapper.v2;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import uz.mamarasulov.rest_sample.model.Order;
import uz.mamarasulov.rest_sample.service.ClientService;
import uz.mamarasulov.rest_sample.web.model.UpsertOrderRequest;

public abstract class OrderMapperDelegate implements OrderMapperV2 {

    @Autowired
    private ClientService databaseClientService;

    @Override
    public Order requestToOrder(UpsertOrderRequest request) {
        Order order = new Order();
        order.setCost(request.getCost());
        order.setProduct(request.getProduct());
        order.setClient(databaseClientService.findById(request.getClientId()));

        return order;
    }

    @Override
    public Order requestToOrder(Long orderId, UpsertOrderRequest request) {
        Order order = requestToOrder(request);
        order.setId(orderId);

        return order;
    }
}
