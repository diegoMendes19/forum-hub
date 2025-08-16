
# FórumHub API (Spring Boot 3 + JPA + MySQL + Flyway + JWT)

API REST para gerenciamento de **tópicos** de fórum (CRUD completo) com autenticação via **Spring Security + JWT**.
Projeto preparado para **Java 17**, **Spring Boot 3**, **MySQL 8** e migrações com **Flyway**.

## 🚀 Funcionalidades
- Criar tópico (`POST /topicos`)
- Listar tópicos (`GET /topicos`) com paginação, ordenação e filtros opcionais (curso/ano)
- Detalhar tópico (`GET /topicos/{id}`)
- Atualizar tópico (`PUT /topicos/{id}`)
- Excluir tópico (`DELETE /topicos/{id}`)
- Autenticação de usuário (`POST /login`) que retorna **JWT**
- Filtro JWT para proteger todas as rotas (exceto `/login`)

## 🧱 Regras de negócio
- Todos os campos do tópico são obrigatórios (título, mensagem, autor, curso)
- **Não permite duplicidade** de tópico com mesmo **título + mensagem** (verificação + constraint única)
- Validações com `@Valid` e `jakarta.validation`

## 🛠️ Tecnologias
- Java 17, Spring Boot 3
- Spring Web, Spring Data JPA
- Spring Security (JWT)
- Flyway (migrations SQL)
- MySQL 8
- Lombok
- Maven 4

## 🗄️ Banco de dados
Crie um banco no MySQL:
```sql
CREATE DATABASE forumhub;
```

Configure as credenciais em `src/main/resources/application.properties`.

As migrations do Flyway rodam automaticamente ao iniciar a aplicação:
- `V1__create_table_topicos.sql`
- `V2__create_table_usuarios.sql` (com usuário admin)
- `V3__add_unique_constraint_topicos.sql`

> Usuário admin padrão inserido pela migration (email/senha):
> - **email:** admin@forumhub.com
> - **senha:** 123456

## ⚙️ Configuração
Arquivo `application.properties` (ajuste usuário/senha do MySQL e a chave JWT):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/forumhub
spring.datasource.username=root
spring.datasource.password=senha

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.hibernate.ddl-auto=validate

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

# JWT
jwt.secret=troque_esta_chave_super_secreta
jwt.expiration-hours=2
server.port=8080
```

## ▶️ Rodando
```bash
# 1) compile
mvn clean package

# 2) execute
mvn spring-boot:run
```

## 🔐 Fluxo de autenticação
1. Faça login:
```http
POST /login
Content-Type: application/json

{
  "email": "admin@forumhub.com",
  "senha": "123456"
}
```
Resposta:
```json
{ "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." }
```

2. Use o token nas próximas requisições (Bearer Token).

## 📚 Endpoints principais

### Criar tópico
```http
POST /topicos
Authorization: Bearer <TOKEN>
Content-Type: application/json

{
  "titulo": "Erro ao rodar projeto",
  "mensagem": "Ao iniciar, recebo erro X no console",
  "autor": "Maria",
  "curso": "Spring Boot"
}
```

### Listar tópicos (paginação + filtros opcionais)
```http
GET /topicos?page=0&size=10&sort=dataCriacao,asc&curso=Spring&ano=2025
Authorization: Bearer <TOKEN>
```

### Detalhar tópico
```http
GET /topicos/1
Authorization: Bearer <TOKEN>
```

### Atualizar tópico
```http
PUT /topicos/1
Authorization: Bearer <TOKEN>
Content-Type: application/json

{
  "titulo": "Erro ao rodar projeto (atualizado)",
  "mensagem": "Detalhes do erro...",
  "autor": "Maria",
  "curso": "Spring Boot"
}
```

### Excluir tópico
```http
DELETE /topicos/1
Authorization: Bearer <TOKEN>
```

## ✅ Testes com Postman/Insomnia
- Faça `POST /login` e copie o token
- Em **Authorization** use **Bearer Token**
- Chame os endpoints de **/topicos**

## 🧩 Estrutura do projeto
```
forumhub/
├─ src/main/java/com/alura/forumhub
│  ├─ ForumhubApplication.java
│  ├─ config/
│  │  ├─ SecurityConfigurations.java
│  │  └─ SecurityFilter.java
│  ├─ controller/
│  │  ├─ AutenticacaoController.java
│  │  └─ TopicoController.java
│  ├─ dto/
│  │  ├─ DadosAutenticacao.java
│  │  ├─ DadosCadastroTopico.java
│  │  └─ DadosDetalhamentoTopico.java
│  ├─ model/
│  │  ├─ EstadoTopico.java
│  │  ├─ Topico.java
│  │  └─ Usuario.java
│  ├─ repository/
│  │  ├─ TopicoRepository.java
│  │  └─ UsuarioRepository.java
│  └─ service/
│     └─ TokenService.java
├─ src/main/resources/
│  ├─ application.properties
│  └─ db/migration/
│     ├─ V1__create_table_topicos.sql
│     ├─ V2__create_table_usuarios.sql
│     └─ V3__add_unique_constraint_topicos.sql
├─ .gitignore
├─ pom.xml
└─ README.md
```
