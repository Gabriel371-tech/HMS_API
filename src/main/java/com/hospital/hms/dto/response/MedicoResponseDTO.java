package com.hospital.hms.dto.response;

public record MedicoResponseDTO(
        Long codmedico,
        String nome,
        String crm,
        EspecialidadeResponseDTO especialidade
) {
}
