# Pagamento

Este projeto utiliza as seguintes tecnologias:

- Maven
- Spring Boot
- Junit/Mockito
- slf4j

## Instalação de Dependências

Acesse a pasta principal do projeto e execute o seguinte comando para instalar as dependências:

```bash
mvn clean install
```

## Iniciar o programa

Para iniciar o programa, execute o comando;
Obs: Para o programa iniciar é necessário que a porta 8080 esteja livre.

```bash
mvn spring-boot:run
```

Após iniciado o programa pode ser acessado pela URL:

localhost:8080/static/index.html

Passo a passo:

Informe quantas pessoas participaram da compra;

Informe o quanto foi gasto no total dos itens por cada pessoa;

Informe o valor de desconto, o sistema aceita o valor no formato "X" e "X%";

Informe o valor da taxa de entrega;

Por fim clicar em calcular pagamento e será retornado o valor que cada pessoa deve pagar;

Obs: Caso não seja inseridos valores de compras, taxa e desconto, o sistema assume o valor 0.
