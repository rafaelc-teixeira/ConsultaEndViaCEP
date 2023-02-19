package com.example.ConsultaEndViaCEP.consulta.controller;

import com.example.ConsultaEndViaCEP.consulta.model.ConsultaEnderecoRequest;
import com.example.ConsultaEndViaCEP.consulta.model.Message;
import com.example.ConsultaEndViaCEP.consulta.service.ConsultaEnderecoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/consulta-endereco")
public class ConsultaEnderecoController {

    private static final String VIACEP_URL = "https://viacep.com.br/ws/%s/json/";

    @Autowired
    private ConsultaEnderecoService consultaEnderecoService;
    @PostMapping
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