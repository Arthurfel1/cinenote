# 🎬 CineNote – Avaliador de Filmes

CineNote é um sistema web simples desenvolvido em **Java (Servlet + JSP)** com **JPA/Hibernate** e interface em HTML/CSS/JavaScript. Ele permite ao usuário cadastrar, avaliar e visualizar filmes com comentários e notas.

---

## 📦 Funcionalidades

- ✅ Cadastrar filmes com nome, nota (1 a 10) e comentário (opcional)
- ✅ Listar todos os filmes avaliados
- ✅ Validação dos campos obrigatórios no front-end
- ✅ Persistência dos dados com banco de dados MySQL via JPA

---

## 🚀 Como executar o projeto

### 1. Pré-requisitos

- Java JDK 11 ou superior
- Apache Tomcat 9 ou 10
- MySQL (ou MariaDB)
- Maven ou NetBeans
- Navegador atualizado (Chrome, Firefox, etc.)

---

### 2. Banco de Dados

Crie o banco e as tabelas usando o script a seguir:

📄 `create_tables.sql`

```sql
CREATE DATABASE IF NOT EXISTS cinenote DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE cinenote;

CREATE TABLE IF NOT EXISTS filme (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    nota INT NOT NULL,
    comentario TEXT
);
