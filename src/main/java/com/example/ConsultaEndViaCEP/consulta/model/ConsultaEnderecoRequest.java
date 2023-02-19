package com.example.ConsultaEndViaCEP.consulta.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsultaEnderecoRequest {

    private String cep;

    @JsonCreator
    public ConsultaEnderecoRequest(@JsonProperty("cep") String cep) {
        this.cep = cep;
    }

    public String getCep() {
        return cep;
    }
}
