# NutriCare — Backend API

REST API para sistema de gestão de consultório de nutrição. Gerencia pacientes, planos alimentares, agendamentos e controle financeiro.

---

## Stack

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot 4.0.6 |
| Banco de dados | PostgreSQL 16 |
| Cache | Redis 7 |
| Migrations | Flyway |
| Segurança | Spring Security + JWT |
| ORM | Spring Data JPA + Hibernate |
| Mapeamento | MapStruct |
| Documentação | OpenAPI / Swagger UI |
| Deploy | AWS ECS + Docker |

---

## Pré-requisitos

- Java 21
- Maven
- Docker Desktop

---

## Configuração local

### 1. Variáveis de ambiente

Copie o arquivo de exemplo e preencha os valores:

```bash
cp .env.example .env
```

Variáveis necessárias:

```
DB_HOST=localhost
DB_PORT=5432
DB_NAME=nutricare
DB_USER=nutricare_user
DB_PASSWORD=
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_PASSWORD=
PGADMIN_EMAIL=
PGADMIN_PASSWORD=
```

### 2. Subir a infraestrutura

```bash
docker compose up -d
```

| Serviço | Porta | Descrição |
|---|---|---|
| PostgreSQL | 5432 | Banco principal |
| Redis | 6379 | Cache |
| PgAdmin | 5050 | Interface visual do banco |

### 3. Rodar a aplicação

```bash
./mvnw spring-boot:run
```

A aplicação sobe na porta `8080`. As migrations do Flyway são executadas automaticamente na inicialização.

---

## Documentação da API

Após subir a aplicação, acesse:

```
http://localhost:8080/swagger-ui.html
```

---

## Estrutura de pacotes

```
src/main/java/com/nutri/nutricare/
├── config/         # Beans de configuração (Security, Redis, etc.)
├── domain/         # Entidades, repositories, services e DTOs por domínio
│   ├── paciente/
│   ├── plano/
│   ├── agendamento/
│   └── financeiro/
├── api/            # Controllers REST
├── infra/          # Integrações externas
└── shared/         # Exceptions e utilitários compartilhados
```

A organização segue o padrão **Package by Feature** — cada domínio contém todos os seus arquivos em uma única pasta.

---

## Endpoints disponíveis

### Pacientes

| Método | Rota | Descrição |
|---|---|---|
| POST | `/pacientes` | Cadastrar paciente |
| GET | `/pacientes` | Listar todos os pacientes |
| GET | `/pacientes/{id}` | Buscar paciente por ID |
| PUT | `/pacientes/{id}` | Atualizar paciente |
| PATCH | `/pacientes/{id}/desativar` | Desativar paciente |

---

## Padrão de commits

```
feat:      funcionalidade nova
fix:       correção de bug
chore:     configuração, dependências, infra
refactor:  melhoria sem mudar comportamento
test:      testes
docs:      documentação
```

---

## Infraestrutura AWS (produção)

| Serviço | Uso |
|---|---|
| ECS + Docker | Containers da aplicação |
| RDS | PostgreSQL gerenciado |
| ElastiCache | Redis gerenciado |
| S3 | Exames e documentos |
| SES + SNS | E-mails e notificações |
| CloudWatch | Logs e alertas |
