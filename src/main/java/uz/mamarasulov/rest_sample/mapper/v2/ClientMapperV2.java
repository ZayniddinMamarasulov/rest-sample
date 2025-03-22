package uz.mamarasulov.rest_sample.mapper.v2;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uz.mamarasulov.rest_sample.model.Client;
import uz.mamarasulov.rest_sample.web.model.ClientListResponse;
import uz.mamarasulov.rest_sample.web.model.ClientResponse;
import uz.mamarasulov.rest_sample.web.model.UpsertClientRequest;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {OrderMapperV2.class})
public interface ClientMapperV2 {

    Client requestToClient(UpsertClientRequest request);

    @Mapping(source = "clientId", target = "id")
    Client requestToClient(Long clientId, UpsertClientRequest request);

    ClientResponse clientToResponse(Client client);

    default ClientListResponse clientListToClientResponseList(List<Client> clients) {
        ClientListResponse response = new ClientListResponse();

        response.setClients(clients.stream()
                .map(this::clientToResponse).collect(Collectors.toList())
        );

        return response;
    }
}
