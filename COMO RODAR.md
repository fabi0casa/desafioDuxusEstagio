````markdown
# 🚀 Projeto - Guia de Execução

Este documento explica como rodar o projeto localmente.

---

## 📋 Pré-requisitos

Antes de começar, você precisa ter instalado na sua máquina:

- 🐳 **Docker**
- ☕ **Java 21**
- 📦 **Maven**

---

## 🐳 Subindo o Banco de Dados com Docker

O projeto utiliza **MySQL** via Docker.

1. Acesse a pasta raiz, onde está o arquivo `docker-compose.yml`:

2. Suba o container do banco:

   ```bash
   docker compose up -d
   ```

3. Verifique se o container está rodando:

   ```bash
   docker ps
   ```

Se estiver tudo certo, o banco estará disponível na porta configurada (geralmente `3306`).

---

## ☕ Iniciando a Aplicação (Backend)

Com o banco rodando, agora é hora de subir o servidor.

1. Certifique-se de que está usando o **Java 21**:

   ```bash
   java -version
   ```

2. Verifique se o Maven está instalado:

   ```bash
   mvn -version
   ```

3. Na raiz do projeto (onde está o `pom.xml`), execute:

   ```bash
   mvn spring-boot:run
   ```

O Maven irá baixar as dependências (caso seja a primeira execução) e iniciar o servidor.

---

## 🌐 Acessando o Projeto

Após o servidor iniciar com sucesso, acesse no navegador:

```
http://localhost:8080
```

As telas do projeto estarão disponíveis nesse endereço.

---

## 🛑 Parando a Aplicação

Para parar o servidor:

* Pressione `CTRL + C` no terminal onde o Maven está rodando.

Para parar o banco:

```bash
docker compose down
```

---

Se houver qualquer problema, verifique:

* Se o Docker está rodando
* Se a porta 3306 (MySQL) não está em uso
* Se a porta 8080 não está em uso
* Se você está utilizando Java 21

```
```
