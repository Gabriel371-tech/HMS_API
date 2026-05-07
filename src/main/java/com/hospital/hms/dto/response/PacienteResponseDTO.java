package com.hospital.hms.dto.response;

import java.time.LocalDate;

public record PacienteResponseDTO(
        Long codpaciente,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        TipoSanguineoResponseDTO tipoSanguineo
) {
}
