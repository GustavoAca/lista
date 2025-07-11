# Projeto Lista de Compras

## Descrição

Este é um projeto de microsserviço para gerenciamento de listas de compras. Ele permite que os usuários criem,
visualizem, atualizem e excluam listas de compras e itens. O projeto utiliza uma arquitetura baseada em Spring Boot e se
integra a outros serviços, como um serviço de usuários, para uma solução completa.

## Tecnologias Utilizadas

* **Java 17**: Versão da linguagem de programação.
* **Spring Boot 3.3.5**: Framework principal para a criação da aplicação.
* **Spring Data JPA**: Para persistência de dados em um banco de dados relacional.
* **PostgreSQL**: Banco de dados utilizado para armazenar os dados.
* **Spring Security com OAuth2**: Para proteger os endpoints da API.
* **Spring Cloud Netflix Eureka**: Para registro e descoberta de serviços.
* **Spring Cloud OpenFeign**: Para comunicação declarativa com outros serviços REST.
* **Redis**: Para caching de dados, melhorando a performance.
* **Flyway**: Para gerenciamento de migrações de banco de dados.
* **Lombok**: Para reduzir código boilerplate.
* **Maven**: Para gerenciamento de dependências e build do projeto.

## Como Executar o Projeto

### Pré-requisitos

* Java 17
* Maven
* Docker (opcional, para rodar o banco de dados)
* Uma instância do Eureka Server rodando
* Um serviço de autenticação (OAuth2)

### Passos

1. **Clone o repositório:**
   ```bash
   git clone <url-do-repositorio>
   cd lista
   ```

2. **Configure o banco de dados:**
    * Crie um banco de dados PostgreSQL.
    * Atualize as configurações de data source no arquivo `src/main/resources/application.yml`.

3. **Configure a conexão com o Eureka e o serviço de autenticação:**
    * Atualize as configurações do Eureka client e do provedor OAuth2 no `src/main/resources/application.yml`.

4. **Execute a aplicação:**
   ```bash
   ./mvnw spring-boot:run
   ```

## Endpoints da API

A API expõe os seguintes endpoints para gerenciar listas de compras:

* `GET /item`: Retorna todos os itens.
* `GET /item/{id}`: Retorna um item específico.
* `POST /item`: Cria um novo item.
* `PUT /item/{id}`: Atualiza um item existente.
* `DELETE /item/{id}`: Deleta um item.
* `GET /item-lista`: Retorna todos os itens da lista.
* `POST /item-lista`: Adiciona um item a uma lista.
* `DELETE /item-lista/{id}`: Remove um item de uma lista.

## Migrações de Banco de Dados

As migrações de banco de dados são gerenciadas pelo Flyway. Os scripts de migração estão localizados em
`src/main/resources/db/migration`.

## Segurança

A segurança da aplicação é feita com Spring Security e OAuth2. Todos os endpoints são protegidos e requerem um token de
acesso válido.

## Service Discovery

O serviço se registra no Eureka Server para que outros serviços possam encontrá-lo e se comunicar com ele.

## Caching

A aplicação utiliza Redis para cachear algumas consultas e melhorar a performance. As configurações de cache podem ser
encontradas em `src/main/java/com/glaiss/lista/config/RedisConfig.java`.
