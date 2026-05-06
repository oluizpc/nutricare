package com.nutri.nutricare.domain.paciente;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutri.nutricare.shared.exception.BusinessException;
import com.nutri.nutricare.shared.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository repository;
    private final PacienteMapper mapper;

    @Transactional
    public PacienteResponseDTO criar(PacienteRequestDTO dto) {
        if (repository.existsByCpf(dto.cpf())) {
            throw new BusinessException("CPF já cadastrado!");
        }

        if (repository.existsByEmail(dto.email())) {
            throw new BusinessException("Email já cadastrado!");
        }

        Paciente paciente = mapper.toEntity(dto);
        Paciente salvo = repository.save(paciente);
        return mapper.toResponse(salvo);
    }


    @Transactional(readOnly = true)
    public PacienteResponseDTO buscarPorId(Long id) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));
        return mapper.toResponse(paciente);
    }

    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional
    public PacienteResponseDTO atualizar(Long id, PacienteRequestDTO dto) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));
        mapper.updateEntity(dto, paciente);
        return mapper.toResponse(paciente);
    }

    @Transactional
    public void desativar(Long id) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));
        paciente.setAtivo(false);
    }


}
