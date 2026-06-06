# <p align="center"><img src="src/main/resources/static/images/logo.png" alt="HMS Logo" width="200"></p>

# HMS - Hospital Management System

O **HMS (Hospital Management System)** é uma solução robusta para o gerenciamento de operações hospitalares fundamentais. Este sistema permite o controle eficiente de pacientes, médicos, especialidades, alas, quartos e leitos, proporcionando uma visão consolidada da ocupação e dos recursos do hospital através de um dashboard intuitivo e uma API RESTful.

## 🚀 Tecnologias Utilizadas

Este projeto foi desenvolvido com as seguintes tecnologias de ponta:

- **Java 17**: Linguagem de programação robusta e escalável.
- **Spring Boot 3.4.0**: Framework para criação de aplicações Java autossuficientes.
  - **Spring Data JPA**: Abstração de acesso a dados.
  - **Spring Web**: Para criação de APIs REST e controladores MVC.
  - **Thymeleaf**: Engine de templates para o front-end dinâmico.
  - **Spring Validation**: Garantia de integridade dos dados.
- **PostgreSQL**: Banco de dados relacional potente.
- **Lombok**: Redução de código boilerplate.
- **Maven**: Gerenciamento de dependências e build.
- **Insomnia**: Ferramenta utilizada para testes e documentação da API.

## 🛠️ Instalação e Configuração

### Pré-requisitos
Certifique-se de ter instalado em sua máquina:
- [JDK 17+](https://www.oracle.com/java/technologies/downloads/)
- [Maven 3.x](https://maven.apache.org/download.cgi)
- [PostgreSQL](https://www.postgresql.org/download/)

### Passo 1: Configurar o Banco de Dados
1. Acesse seu terminal do PostgreSQL ou ferramenta gráfica (como PGAdmin).
2. Crie um banco de dados chamado `hms_db`:
   ```sql
   CREATE DATABASE hms_db;
   ```
3. Verifique se o usuário `postgres` e a senha `123456` estão configurados ou ajuste o arquivo `src/main/resources/application.properties` conforme sua necessidade.

### Passo 2: Clonar e Compilar
```bash
# Clone o repositório
git clone <url-do-repositorio>

# Entre na pasta do projeto
cd hms

# Compile o projeto e baixe as dependências
mvn clean install
```

### Passo 3: Executar a Aplicação
```bash
mvn spring-boot:run
```
A aplicação estará disponível em: `http://localhost:8080`

## 📖 Como Usar

### Interface Web (Dashboard)
Acesse `http://localhost:8080` no seu navegador para utilizar a interface administrativa.
- **Login Inicial**: Utilize as credenciais cadastradas ou crie um novo usuário na página de `/cadastro`.
- **Navegação**: Use o menu lateral para gerenciar Médicos, Pacientes e a Infraestrutura (Alas e Quartos).

### API REST
Para desenvolvedores e integração, o sistema disponibiliza endpoints JSON sob o prefixo `/api/`.
- **Exemplos**:
  - `GET /api/pacientes` - Listar todos os pacientes.
  - `POST /api/medicos` - Cadastrar um novo médico.

### Testando com Insomnia
Importe o arquivo `insomnia_hms_collection.json` localizado na raiz deste projeto para o seu **Insomnia** para ter acesso imediato a todos os testes de CRUD documentados.

## 📂 Estrutura do Projeto
- `src/main/java`: Código fonte Java (Model, Repository, Service, Controller).
- `src/main/resources/templates`: Páginas HTML (Thymeleaf).
- `src/main/resources/static`: Ativos estáticos (Imagens, CSS, JS).
- `insomnia_hms_collection.json`: Coleção de testes de API.

---
Desenvolvido para facilitar a gestão hospitalar com eficiência e segurança.
