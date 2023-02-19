package com.example.ConsultaEndViaCEP.consulta.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class ConsultaEnderecoService {
    public boolean validarCep(String cep) {
        // Remove any non-numeric characters from the CEP string
        cep = cep.replaceAll("[^\\d]", "");

        // Check if the resulting string has the correct length (either 8 or 9 digits)
        if (cep.length() != 8 && cep.length() != 9) {
            return false;
        }

        // If the CEP has 9 digits, the 5th character must be a hyphen
        if (cep.length() == 9 && cep.charAt(5) != '-') {
            return false;
        }

        return true;
    }

    public ResponseEntity<String> getResponseFromViacep(String cep, String VIACEP_URL) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(VIACEP_URL, cep);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.getBody());
                if (rootNode.has("erro")) {
                    return null;
                }
            } catch (IOException e) {
                // handle the exception
            }
            return ResponseEntity.ok(response.getBody());
        }
        return null;
    }


}
