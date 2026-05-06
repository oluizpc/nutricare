package com.nutri.nutricare.domain.paciente;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record PacienteRequestDTO(

    @NotBlank
    @Size(max = 150)
    String nome,

    @NotBlank
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF inválido")
    String cpf,

    LocalDate dataNascimento,

    PacienteSexo sexo,

    @NotBlank
    @Email
    @Size(max = 150)
    String email,

    @NotBlank
    @Size(max = 20)
    String telefone,

    String objetivo,

    String observacao
) {}
