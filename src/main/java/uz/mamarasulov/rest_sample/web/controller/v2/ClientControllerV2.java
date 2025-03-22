package uz.mamarasulov.rest_sample.web.controller.v2;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mamarasulov.rest_sample.mapper.v2.ClientMapperV2;
import uz.mamarasulov.rest_sample.model.Client;
import uz.mamarasulov.rest_sample.service.ClientService;
import uz.mamarasulov.rest_sample.web.model.ClientListResponse;
import uz.mamarasulov.rest_sample.web.model.ClientResponse;
import uz.mamarasulov.rest_sample.web.model.UpsertClientRequest;

@RestController
@RequestMapping("/api/v2/client")
@RequiredArgsConstructor
public class ClientControllerV2 {

    private final ClientService databaseClientService;

    private final ClientMapperV2 clientMapperV2;

    @GetMapping
    public ResponseEntity<ClientListResponse> findAll() {
        return ResponseEntity.ok(
                clientMapperV2.clientListToClientResponseList(
                        databaseClientService.findAll()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(clientMapperV2.clientToResponse(
                databaseClientService.findById(id)
        ));
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody @Valid UpsertClientRequest request) {
        Client newClient = databaseClientService.save(clientMapperV2.requestToClient(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientMapperV2.clientToResponse(newClient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable("id") Long clientId,
                                                 @RequestBody @Valid UpsertClientRequest request) {
        Client updatedClient = databaseClientService.update(clientMapperV2.requestToClient(clientId, request));

        return ResponseEntity.ok(clientMapperV2.clientToResponse(updatedClient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        databaseClientService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
