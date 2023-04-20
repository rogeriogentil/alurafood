# alurafood-pagamentos (por Rogerio J.)

Aplicação microsserviço referente ao domínio de pagamentos.

# Pré-requisitos

- JDK 17
- Docker

## _Build_

    ./mvnw clean install

## Construção da imagem do SGBD

    docker build -t alurafood-pagamentos-db .

# Execução

1. Rodar o container do SGBD:

    docker run -d --name alurafood-pagamentos-db -p 3306:3306 alurafood-pagamentos-db:latest

2. Inicilizar aplicação 

    ./mvw spring-boot:run


