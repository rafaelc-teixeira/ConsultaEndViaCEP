package com.example.ConsultaEndViaCEP;

import com.example.ConsultaEndViaCEP.consulta.controller.ConsultaEnderecoController;
import com.example.ConsultaEndViaCEP.consulta.model.ConsultaEnderecoRequest;
import com.example.ConsultaEndViaCEP.consulta.model.ConsultaEnderecoResponse;
import com.example.ConsultaEndViaCEP.consulta.model.Message;
import com.example.ConsultaEndViaCEP.consulta.service.ConsultaEnderecoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    @Test
    public void testConsultaEnderecoWithValidCep() throws JsonProcessingException {
        ConsultaEnderecoController controller = mock(ConsultaEnderecoController.class);
        ConsultaEnderecoRequest request = new ConsultaEnderecoRequest("30530160");

        ConsultaEnderecoResponse consultaEnderecoResponse = new ConsultaEnderecoResponse(
                "30530-160",
                "Rua Barão de Guaxupé",
                "",
                "João Pinheiro",
                "Belo Horizonte",
                "MG",
                7.85);
        when(controller.consultaEndereco(Mockito.eq(request))).thenReturn(new ResponseEntity<>(new ObjectMapper().writeValueAsString(consultaEnderecoResponse), HttpStatus.OK));

        ResponseEntity<String> responseEntity = controller.consultaEndereco(request);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(new ObjectMapper().writeValueAsString(consultaEnderecoResponse), responseEntity.getBody());
    }

}