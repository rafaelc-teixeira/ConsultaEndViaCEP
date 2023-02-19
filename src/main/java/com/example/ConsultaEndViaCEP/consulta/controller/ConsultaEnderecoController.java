package com.example.ConsultaEndViaCEP.consulta.controller;

import com.example.ConsultaEndViaCEP.consulta.model.ConsultaEnderecoRequest;
import com.example.ConsultaEndViaCEP.consulta.model.ConsultaEnderecoResponse;
import com.example.ConsultaEndViaCEP.consulta.service.ConsultaEnderecoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Autowired
    private ConsultaEnderecoService consultaEnderecoService;
    @PostMapping
    public ResponseEntity<Map<String, Object>> consultaEndereco(@RequestBody ConsultaEnderecoRequest request) {
        String cep = request.getCep();
        Map<String, Object> result = new HashMap<>();

        if (!consultaEnderecoService.validarCep(cep)) {
            result.put("Message: ", "CEP inválido.");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        // TODO: realizar a consulta do endereço correspondente ao CEP

        String rua = "Praça da Sé";
        String complemento = "lado ímpar";
        String bairro = "Sé";
        String cidade = "São Paulo";
        String estado = "SP";

        result.put("cep", cep);
        result.put("rua", rua);
        result.put("complemento", complemento);
        result.put("bairro", bairro);
        result.put("cidade", cidade);
        result.put("estado", estado);
        result.put("frete"  , 7.85);

        double frete = 7.85;
        // TODO: realizar o cálculo do frete

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}