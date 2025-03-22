package uz.mamarasulov.rest_sample.web.controller.v2;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mamarasulov.rest_sample.mapper.v2.OrderMapperV2;
import uz.mamarasulov.rest_sample.model.Order;
import uz.mamarasulov.rest_sample.service.OrderService;
import uz.mamarasulov.rest_sample.web.model.OrderListResponse;
import uz.mamarasulov.rest_sample.web.model.OrderResponse;
import uz.mamarasulov.rest_sample.web.model.UpsertOrderRequest;

@RestController
@RequestMapping("/api/v2/order")
@RequiredArgsConstructor
public class OrderControllerV2 {

    private final OrderService databaseOrderService;

    private final OrderMapperV2 orderMapperV2;


    @GetMapping
    public ResponseEntity<OrderListResponse> findAll() {
        return ResponseEntity.ok(
                orderMapperV2.orderListToOrderListResponse(databaseOrderService.findAll())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                orderMapperV2.orderToResponse(databaseOrderService.findById(id))
        );
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody @Valid UpsertOrderRequest request) {
        Order newOrder = databaseOrderService.save(orderMapperV2.requestToOrder(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderMapperV2.orderToResponse(newOrder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> update(@PathVariable("id") Long orderId,
                                                @RequestBody @Valid UpsertOrderRequest request) {

        Order updatedOrder = databaseOrderService
                .update(orderMapperV2.requestToOrder(orderId, request));

        return ResponseEntity.ok(orderMapperV2.orderToResponse(updatedOrder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        databaseOrderService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
