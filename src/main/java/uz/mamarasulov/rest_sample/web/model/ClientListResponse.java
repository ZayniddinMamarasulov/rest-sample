package uz.mamarasulov.rest_sample.web.model;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClientListResponse {

    private List<ClientResponse> clients = new ArrayList<>();
}
