package com.hospital.hms.dto.request;

public record MedicoRequestDTO(
        String nome,
        String crm,
        Long codespecialidade,
        EspecialidadeReferenceDTO especialidade
) {
}
