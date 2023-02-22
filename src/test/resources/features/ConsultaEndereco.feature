Feature: Consulta de endereço

  Scenario: Consultar endereço com CEP válido
    Given um CEP válido "30530160"
    When realizar a consulta de endereço
    Then o código de status da resposta deve ser "200"
    And o corpo da resposta deve ser uma string JSON contendo:
      """
      {
        "cep": "30530-160",
        "rua": "Rua Barão de Guaxupé",
        "complemento": "",
        "bairro": "João Pinheiro",
        "localidade": "Belo Horizonte",
        "uf": "MG",
        "preco": 7.85
      }
      """

  Scenario: Consultar endereço com CEP inválido
    Given um CEP inválido "12345-6781"
    When realizar a consulta de endereço
    Then o código de status da resposta deve ser "400"
    And o corpo da resposta deve ser uma string JSON contendo:
      """
      {
        "mensagem": "CEP inválido"
      }
      """

  Scenario: Consultar endereço com CEP inexistente
    Given um CEP inexistente "99999-999"
    When realizar a consulta de endereço
    Then o código de status da resposta deve ser "400"
    And o corpo da resposta deve ser uma string JSON contendo:
      """
      {
        "mensagem": "Não foi possível encontrar o endereço correspondente ao CEP informado"
      }
      """