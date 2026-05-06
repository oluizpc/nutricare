package com.nutri.nutricare.domain.paciente;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PacienteMapper {

    Paciente toEntity(PacienteRequestDTO dto);

    PacienteResponseDTO toResponse(Paciente paciente);

    void updateEntity(PacienteRequestDTO dto, @MappingTarget Paciente paciente);
}
