# 🛒 Market API (Backend)

API backend robusta desenvolvida para a gestão completa de um estabelecimento comercial, focada em boas práticas de mercado, segurança, controle rigoroso de estoque por lotes e arquitetura modular.

---

## 🚀 Tecnologias Utilizadas

* **Java 21**
* **Spring Boot** (Web, Data JPA, Security)
* **Spring Security + JWT** (Autenticação e controle de acesso baseado em Roles: `ADMIN`, `USER`)
* **PostgreSQL** (Banco de dados relacional)
* **Lombok** (Redução de código boilerplate)
* **Springdoc OpenAPI / Swagger** (Documentação interativa da API)
* **Bean Validation** (Validação de dados de entrada)

---

## 📦 Módulos Atuais (Implementados)

1. **Autenticação (`/auth`)**:
   * Cadastro de usuários com criptografia de senha (`BCrypt`).
   * Login com emissão e validação de tokens **JWT**.
   * Suporte a perfis de acesso (`ADMIN`, `USER`).
2. **Produtos (`/api/v1/products`)**:
   * Cadastro, consulta por ID e por SKU com validações de unicidade.
3. **Estoque e Lotes (`/api/v1/stocks` & `/api/v1/batches`)**:
   * Gestão de estoque consolidado por produto com controle de estoque mínimo.
   * Gestão de lotes (`Batch`) com preços de compra, quantidade e datas de validade.
   * Alertas automáticos para produtos abaixo do estoque mínimo e lotes próximos do vencimento.

---

## 🗺️ Roadmap / Próximas Funcionalidades

O projeto está em constante evolução. As próximas etapas da arquitetura incluem:

* **[ ] Módulo de Vendas (`/api/v1/sales`)**: Registro de transações de venda, baixa automática no estoque por lotes (FIFO/LIFO) e cálculo de margem de lucro.
* **[ ] Módulo de Relatórios (`/api/v1/reports`)**: Geração de relatórios gerenciais de vendas, valoração de estoque e produtos mais vendidos.
* **[ ] Camada de Inteligência Artificial (`Spring AI`)**: Assistente inteligente para sugestões automatizadas de reposição de estoque e insights baseados no fluxo de vendas.
* **[ ] Camada de Mensageria (`RabbitMQ`)**: Envio de eventos assíncronos para notificações de estoque crítico e lotes prestes a vencer.

---

## ⚙️ Como Executar o Projeto Localmente

### Pré-requisitos
* **Java 21** instalado na sua máquina.
* **Maven** (ou utilize o `mvnw` incluso no projeto).
* **PostgreSQL** rodando localmente.

### 1. Configurar o Banco de Dados
Crie um banco de dados no PostgreSQL chamado `market_db`:

```sql
CREATE DATABASE market_db;
```

### 2. Configurar as Credenciais
Abra o arquivo `src/main/resources/application.properties` e ajuste o usuário e senha do seu PostgreSQL, se necessário:

```properties
spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/market_db
spring.datasource.username=postgres
spring.datasource.password=postgrespass
```

### 3. Executar a Aplicação
Na raiz do projeto pelo terminal, execute:

```bash
./mvnw spring-boot:run
```
*(No Windows, caso utilize o prompt de comando padrão, pode usar `mvnw.cmd spring-boot:run`)*

---

## 📄 Documentação da API (Swagger)

Com a aplicação rodando, você pode acessar a documentação interativa completa de todos os endpoints através do Swagger UI:

👉 `http://localhost:8080/swagger-ui/index.html`

---

## 👨‍💻 Autor

Desenvolvido por **Deyvid** 🚀