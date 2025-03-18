package uz.mamarasulov.rest_sample.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClientResponse {

    private Long id;

    private String name;

    private List<OrderResponse> orders = new ArrayList<>();
}
