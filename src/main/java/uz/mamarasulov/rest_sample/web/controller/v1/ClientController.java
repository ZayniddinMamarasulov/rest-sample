package uz.mamarasulov.rest_sample.web.controller.v1;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import uz.mamarasulov.rest_sample.mapper.v1.ClientMapper;
import uz.mamarasulov.rest_sample.model.Client;
import uz.mamarasulov.rest_sample.repository.ClientRepository;
import uz.mamarasulov.rest_sample.service.ClientService;
import uz.mamarasulov.rest_sample.web.model.ClientListResponse;
import uz.mamarasulov.rest_sample.web.model.ClientResponse;
import uz.mamarasulov.rest_sample.web.model.UpsertClientRequest;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
@Tag(name = "Client V1", description = "Client API version V1")
public class ClientController {

    private final ClientService clientServiceImpl;

    private final ClientMapper clientMapper;

    @GetMapping
    public ResponseEntity<ClientListResponse> findAll() {
        return ResponseEntity.ok(
                clientMapper.clientListToClientResponseList(clientServiceImpl.findAll()));
    }

    @Operation(
            summary = "Get Client by ID",
            description = "Get Client by ID, Return id, name and list of orders",
            tags = {"client", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = ClientResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class))
                    })
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(clientMapper.clientToResponse(clientServiceImpl.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody UpsertClientRequest request) {
        Client newClient = clientServiceImpl.save(clientMapper.requestToClient(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientMapper.clientToResponse(newClient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable("id") Long clientId,
                                                 @RequestBody UpsertClientRequest request) {

        Client updatedClient = clientServiceImpl.update(clientMapper.requestToClient(clientId, request));

        return ResponseEntity.ok(clientMapper.clientToResponse(updatedClient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientServiceImpl.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
