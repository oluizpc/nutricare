package com.nutri.nutricare.domain.paciente;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PacienteResponseDTO(
    Long id,
    String nome,
    String cpf,
    LocalDate dataNascimento,
    PacienteSexo sexo,
    String email,
    String telefone,
    String objetivo,
    String observacao,
    Boolean ativo,
    LocalDateTime dataCadastro,
    LocalDateTime dataAtualizacao
) {}
