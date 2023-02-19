package com.example.ConsultaEndViaCEP.consulta.service;

import com.example.ConsultaEndViaCEP.consulta.model.ConsultaEnderecoResponse;
import com.example.ConsultaEndViaCEP.consulta.model.ViaCepResponse;
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

    public ResponseEntity<String> getResponseFromViacep(String cep, String VIACEP_URL) throws JsonProcessingException {
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
            ObjectMapper objectMapper = new ObjectMapper();
            ViaCepResponse viaCepResponse = objectMapper.readValue(response.getBody(), ViaCepResponse.class);
            ConsultaEnderecoResponse endereco = new ConsultaEnderecoResponse(
                    viaCepResponse.getCep(),
                    viaCepResponse.getLogradouro(),
                    viaCepResponse.getComplemento(),
                    viaCepResponse.getBairro(),
                    viaCepResponse.getLocalidade(),
                    viaCepResponse.getUf(),
                    getFreteValue(viaCepResponse.getUf()));

            return ResponseEntity.ok(objectMapper.writeValueAsString(endereco));
        }
        return null;
    }


//    public ResponseEntity<ConsultaEnderecoResponse> getEnderecoFromViacep(String cep, String VIACEP_URL) {
//        RestTemplate restTemplate = new RestTemplate();
//        String url = String.format(VIACEP_URL, cep);
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//
//        if (!response.getStatusCode().is2xxSuccessful()) {
//            return null;
//        }
//
//        try {
//            ConsultaEnderecoResponse endereco = convertJsonToConsultaEndereco(response.getBody());
//            if (endereco.hasErro()) {
//                return null;
//            }
//            double frete = getFreteValue(endereco.getUf());
//            endereco.setFrete(frete);
//            return ResponseEntity.ok(endereco);
//        } catch (JsonProcessingException e) {
//            // handle the exception
//        }
//
//        return null;
//    }
//
//    private ConsultaEnderecoResponse convertJsonToConsultaEndereco(String json) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readValue(json, ConsultaEnderecoResponse.class);
//    }

    private double getFreteValue(String uf) {
        switch (uf) {
            case "SP":
            case "RJ":
            case "ES":
            case "MG":
                return 7.85;
            case "DF":
            case "GO":
            case "MS":
            case "MT":
                return 12.5;
            case "AL":
            case "BA":
            case "CE":
            case "MA":
            case "PB":
            case "PE":
            case "PI":
            case "RN":
            case "SE":
                return 15.98;
            case "PR":
            case "RS":
            case "SC":
                return 17.3;
            case "AC":
            case "AM":
            case "AP":
            case "PA":
            case "RO":
            case "RR":
            case "TO":
                return 20.83;
            default:
                return 0.0;
        }
    }


}
