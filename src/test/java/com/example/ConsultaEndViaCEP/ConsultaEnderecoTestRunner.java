package com.example.ConsultaEndViaCEP;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:ConsultaEndereco.feature", glue = "com.example.ConsultaEndViaCep")
public class ConsultaEnderecoTestRunner {
}
