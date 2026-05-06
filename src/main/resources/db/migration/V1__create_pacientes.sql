CREATE TABLE pacientes (
    id               BIGSERIAL       PRIMARY KEY,
    nome             VARCHAR(150)    NOT NULL,
    cpf              VARCHAR(14)     NOT NULL UNIQUE,
    data_nascimento  DATE,
    sexo             VARCHAR(20),
    email            VARCHAR(150)    NOT NULL UNIQUE,
    telefone         VARCHAR(20)     NOT NULL,
    objetivo         TEXT,
    observacao       TEXT,
    ativo            BOOLEAN         NOT NULL DEFAULT TRUE,
    data_cadastro    TIMESTAMP,
    data_atualizacao TIMESTAMP
);
