# NutriCare — Backend API

Sistema de gestão para consultório de nutrição. Desenvolvido para gerenciar pacientes,
planos alimentares, agendamentos e controle financeiro.

---

## Stack tecnológica

- **Linguagem:** Java 21 (LTS)
- **Framework:** Spring Boot 4.0.6
- **Build:** Maven
- **Banco de dados:** PostgreSQL 16
- **Cache:** Redis 7
- **Migrations:** Flyway
- **Segurança:** Spring Security + JWT
- **ORM:** Spring Data JPA + Hibernate
- **Mapeamento:** MapStruct
- **Boilerplate:** Lombok
- **Testes:** JUnit 5 + Mockito
- **Documentação:** OpenAPI / Swagger
- **Deploy:** AWS ECS + Docker
- **CI/CD:** GitHub Actions

---

## Estrutura de pacotes

```
src/main/java/com/nutri/nutricare/
├── config/                  # Beans de configuração (Security, Redis, CORS, etc.)
├── domain/                  # Entidades JPA + regras de negócio por domínio
│   ├── paciente/
│   │   ├── Paciente.java
│   │   ├── PacienteRepository.java
│   │   ├── PacienteService.java
│   │   └── PacienteDTO.java
│   ├── plano/
│   │   ├── PlanoAlimentar.java
│   │   ├── PlanoAlimentarRepository.java
│   │   ├── PlanoAlimentarService.java
│   │   └── PlanoAlimentarDTO.java
│   ├── agendamento/
│   │   ├── Agendamento.java
│   │   ├── AgendamentoRepository.java
│   │   ├── AgendamentoService.java
│   │   └── AgendamentoDTO.java
│   └── financeiro/
│       ├── Lancamento.java
│       ├── LancamentoRepository.java
│       ├── LancamentoService.java
│       └── LancamentoDTO.java
├── api/                     # Controllers REST (camada HTTP)
│   ├── PacienteController.java
│   ├── PlanoController.java
│   ├── AgendamentoController.java
│   └── FinanceiroController.java
├── infra/                   # Integrações externas, configs de infra
└── shared/                  # Exceptions, utils, classes compartilhadas
    ├── exception/
    │   ├── GlobalExceptionHandler.java
    │   ├── ResourceNotFoundException.java
    │   └── BusinessException.java
    └── util/
```

---

## Domínios do sistema

### Paciente
Cadastro completo de pacientes com histórico clínico e evolução.
- Dados pessoais, contato, anamnese
- Histórico de consultas e medidas antropométricas
- Vinculado a planos alimentares e agendamentos

### Plano alimentar
Criação e gestão de planos nutricionais personalizados.
- Refeições do dia com horários e alimentos
- Metas calóricas e de macronutrientes
- Baseado na tabela TACO/IBGE (cacheada no Redis)

### Agendamento
Controle de consultas e lembretes.
- Agenda da nutricionista com disponibilidade
- Confirmação e cancelamento de consultas
- Notificações via e-mail (AWS SES)

### Financeiro
Controle de receitas e relatórios.
- Registro de pagamentos por consulta
- Relatórios mensais e anuais
- Controle de inadimplência

---

## Ambiente local

### Pré-requisitos
- Docker Desktop instalado e rodando
- Java 21
- Maven

### Subir o ambiente
```bash
docker compose up -d
```

### Containers disponíveis
| Serviço    | Container            | Porta  | Descrição              |
|------------|----------------------|--------|------------------------|
| PostgreSQL | nutricare-postgres   | 5432   | Banco principal        |
| Redis      | nutricare-redis      | 6379   | Cache e sessões        |
| PgAdmin    | nutricare-pgadmin    | 5050   | Interface visual do DB |

### PgAdmin
- URL: http://localhost:5050
- Email: `admin@nutricare.com`
- Senha: definida no `.env`

### Conexão com o banco no PgAdmin
- Host: `nutricare-postgres`
- Port: `5432`
- Database: `nutricare`
- Username: `nutricare_user`
- Password: definida no `.env`

---

## Variáveis de ambiente

O projeto usa um arquivo `.env` na raiz (nunca sobe pro GitHub).
Copie o `.env.example` e preencha os valores:

```bash
cp .env.example .env
```

Variáveis necessárias:
```
DB_NAME=nutricare
DB_USER=nutricare_user
DB_PASSWORD=...
REDIS_PASSWORD=...
PGADMIN_EMAIL=...
PGADMIN_PASSWORD=...
```

---

## Migrations (Flyway)

Ficam em `src/main/resources/db/migration/`.
Nomenclatura obrigatória: `V{numero}__{descricao}.sql`

Exemplos:
```
V1__create_pacientes.sql
V2__create_planos_alimentares.sql
V3__create_agendamentos.sql
V4__create_financeiro.sql
```

O Flyway roda automaticamente ao iniciar a aplicação.
O JPA está configurado com `ddl-auto: validate` — ele só valida, nunca altera o schema.

---

## Decisões de arquitetura

### Organização por domínio
Cada domínio tem seus próprios arquivos (entidade, repository, service, DTO) na mesma pasta.
Não usar organização por tipo (todas entidades juntas, todos services juntos).

### DTOs obrigatórios
Nunca expor entidades JPA diretamente nos controllers.
Sempre usar DTOs para entrada e saída da API.
Usar MapStruct para o mapeamento.

### LGPD
Dados de pacientes são sensíveis. Observar:
- Criptografia em campos sensíveis (CPF, dados de saúde)
- Log de auditoria nas entidades
- Controle de acesso por papel (nutricionista vs paciente)

### Perfis Spring
- `local` — desenvolvimento local com Docker
- `prod` — produção na AWS

### Padrão de commits (Conventional Commits)
- `feat:` — funcionalidade nova
- `fix:` — correção de bug
- `chore:` — configuração, dependências, infra
- `refactor:` — melhoria sem mudar comportamento
- `test:` — testes
- `docs:` — documentação

---

## Próximos passos (backlog técnico)

- [ ] Configurar `application.yml` com perfis local e prod
- [ ] Implementar exception handler global (`@RestControllerAdvice`)
- [ ] Criar primeira migration Flyway (tabelas base)
- [ ] Configurar Spring Security + JWT
- [ ] Implementar CRUD de Paciente (primeiro domínio)
- [ ] Implementar CRUD de Agendamento
- [ ] Implementar Plano Alimentar
- [ ] Implementar módulo Financeiro
- [ ] Configurar AWS SES para notificações
- [ ] Configurar CI/CD com GitHub Actions
- [ ] Deploy no AWS ECS

---

## Infraestrutura AWS (produção)

| Serviço       | Uso                        |
|---------------|----------------------------|
| ECS + Docker  | Containers da aplicação    |
| RDS           | PostgreSQL gerenciado       |
| ElastiCache   | Redis gerenciado            |
| S3            | Exames e documentos         |
| SES + SNS     | E-mails e notificações      |
| CloudWatch    | Logs e alertas              |