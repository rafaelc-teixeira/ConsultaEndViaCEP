package com.example.ConsultaEndViaCEP.consulta.controller;

import com.example.ConsultaEndViaCEP.consulta.model.ConsultaEnderecoRequest;
import com.example.ConsultaEndViaCEP.consulta.model.Message;
import com.example.ConsultaEndViaCEP.consulta.service.ConsultaEnderecoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/consulta-endereco")
public class ConsultaEnderecoController {

    private static final String VIACEP_URL = "https://viacep.com.br/ws/%s/json/";

    @Autowired
    public ConsultaEnderecoService consultaEnderecoService;

    /**
     * Consulta o endereço correspondente ao CEP informado.
     *
     * @param request Objeto que representa a requisição de consulta de endereço.
     * @return O endereço correspondente ao CEP informado.
     *
     * @tag Consulta de endereço
     */
    @PostMapping
    @Operation(summary = "Consulta o endereço correspondente ao CEP informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço correspondente ao CEP informado encontrado"),
            @ApiResponse(responseCode = "400", description = "Não foi possível encontrar o endereço correspondente ao CEP informado ou CEP inválido",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<String> consultaEndereco(@RequestBody ConsultaEnderecoRequest request) throws JsonProcessingException {
        String cep = request.getCep();

        if (!consultaEnderecoService.validarCep(cep)) {
            return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(new Message("CEP inválido")));
        }

        ResponseEntity<String> response = consultaEnderecoService.getResponseFromViacep(cep, VIACEP_URL);
        if (response == null) {
            return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(new Message("Não foi possível encontrar o endereço correspondente ao CEP informado")));
        }

        return ResponseEntity.ok(response.getBody());
    }
}