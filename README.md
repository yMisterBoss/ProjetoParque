
# 🚗 Sistema de Reservas de Estacionamento

Este é um sistema web completo com:
- Backend em **Ktor (Kotlin)** com autenticação JWT
- Frontend em **HTML/CSS/JS** (mockup) ou React (em desenvolvimento)
- Funcionalidades para **utilizadores** e **administradores**

## 📁 Estrutura do Projeto

```
/backend           # API em Ktor
/frontend          # Interface web (HTML/CSS ou React)
README.md
```

## ⚙️ Requisitos

- **JDK 17 ou superior**
- **Gradle (Kotlin DSL)**
- **MySQL** (ou compatível)
- **Node.js e npm** (para frontend em React)
- IDE recomendada: IntelliJ IDEA

## 🛠️ Configuração do Backend

1. **Base de Dados**

Cria a base de dados `esan-dsg11` no MySQL e a tabela `Utilizadores`:

```sql
CREATE DATABASE `esan-dsg11`;

CREATE TABLE `Utilizadores` (
  `idUtilizadores` INT AUTO_INCREMENT PRIMARY KEY,
  `Nome_Utilizador` VARCHAR(245),
  `Tipo` VARCHAR(45),
  `Data_Criacao` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `Email` VARCHAR(100) UNIQUE,
  `Password` VARCHAR(255)
);
```

2. **Configuração**

Verifica o ficheiro `Application.kt`:
```kotlin
val url = "jdbc:mysql://localhost:3306/esan-dsg11"
val user = "root"
val password = ""
```

3. **Compilar e Executar**

```bash
./gradlew run
```

A API estará em: `http://localhost:8080`

## 📌 Endpoints da API

### Autenticação

- `POST /api/login`: Faz login e devolve o token JWT
- `GET /api/me`: Devolve os dados do utilizador autenticado

### Utilizadores

- `GET /utilizadores`: Lista todos os utilizadores
- `POST /utilizadores`: Regista novo utilizador

## 🔐 JWT

- Secret: `"secret"`
- Issuer e Audience: `"http://localhost:8080/"`

No frontend, o token deve ser incluído assim:

```js
Authorization: Bearer <token>
```

## 💻 Frontend

1. Abre `/frontend/index.html` no browser  
   Ou usa `npm start` no React (caso uses versão React)

2. Páginas incluídas:

- `Início`
- `Sobre`
- `Contato`
- `Login`
- `Registrar`

## 👮‍♂️ Admin

- Acesso a estatísticas e gestão de utilizadores
- Verificação do tipo do utilizador (`tipo == "admin"`) no JWT ou após login

## 🧪 Testes

```bash
./gradlew test
```