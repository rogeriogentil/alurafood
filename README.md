# alurafood (por Rogerio J.)

Esse é um projeto de aplicações de microsserviços desenvolvidas durante o curso 
[Microsserviços na prática: Implementando com Java e Spring](https://cursos.alura.com.br/course/microsservicos-implementando-java-spring)
da Alura.

# Aplicações

## alurafood-server

Aplicação que executa o _service discovery_ usando Eureka do 
[Spring Cloud Netlfix](https://spring.io/projects/spring-cloud-netflix).

## alurafood-gateway

Aplicação que executa o serviço de _gateway_ e _loading balancing_ usando 
[Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway).

## alurafood-pagamentos

Aplicação microsserviço referente ao domínio de pagamentos.

## alurafood-pedidos

Aplicação microsserviço referente ao domínio de pedidos.

# Como executar

1. Executar `alurafood-server`
2. Executar `alurafood-gateway`
3. Executar `alurafood-pagamentos` e `alurafood-pedidos`
