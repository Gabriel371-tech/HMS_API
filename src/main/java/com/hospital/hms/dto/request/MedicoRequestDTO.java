package com.hospital.hms.dto.request;

public record MedicoRequestDTO(
        String nome,
        String crm,
        String telefone,
        Long codespecialidade,
        EspecialidadeReferenceDTO especialidade
) {
}
