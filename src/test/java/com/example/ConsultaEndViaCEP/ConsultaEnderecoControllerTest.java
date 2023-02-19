package com.example.ConsultaEndViaCEP;

import com.example.ConsultaEndViaCEP.consulta.controller.ConsultaEnderecoController;
import com.example.ConsultaEndViaCEP.consulta.model.ConsultaEnderecoRequest;
import com.example.ConsultaEndViaCEP.consulta.model.Message;
import com.example.ConsultaEndViaCEP.consulta.service.ConsultaEnderecoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ConsultaEnderecoControllerTest {

    @Test
    public void testConsultaEnderecoWithInvalidCep() throws JsonProcessingException {
        ConsultaEnderecoService consultaEnderecoService = mock(ConsultaEnderecoService.class);
        ConsultaEnderecoRequest request = new ConsultaEnderecoRequest("1234567");

        ConsultaEnderecoController controller = new ConsultaEnderecoController();
        controller.consultaEnderecoService = consultaEnderecoService;

        ResponseEntity<String> responseEntity = controller.consultaEndereco(request);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals((new ObjectMapper().writeValueAsString(new Message("CEP inválido"))), responseEntity.getBody());
    }

}