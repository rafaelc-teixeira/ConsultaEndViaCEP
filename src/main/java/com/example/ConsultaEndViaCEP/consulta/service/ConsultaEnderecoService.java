package com.example.ConsultaEndViaCEP.consulta.service;

import org.springframework.stereotype.Service;

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
}
