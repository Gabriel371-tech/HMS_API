package com.hospital.hms.dto.response;

import java.time.LocalDate;

public record PacienteResponseDTO(
        Long codpaciente,
        String nome,
        LocalDate datanasc,
        TipoSanguineoResponseDTO tipoSanguineo
) {
}
