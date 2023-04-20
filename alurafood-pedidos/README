# alurafood-pagamentos (por Rogerio J.)

Aplicação microsserviço referente ao domínio de pedidos.

# Pré-requisitos

- JDK 17
- Docker

## _Build_

    ./mvnw clean install

## Construção da imagem do SGBD

    docker build -t alurafood-pedidos-db .

# Execução

1. Rodar o container do SGBD:

    docker run -d --name alurafood-pedidos-db -p 3307:3306 alurafood-pedidos-db:latest

2. Inicilizar aplicação 

    ./mvw spring-boot:run


