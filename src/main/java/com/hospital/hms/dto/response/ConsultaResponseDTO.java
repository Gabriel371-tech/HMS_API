package com.hospital.hms.dto.response;

import java.time.LocalDateTime;

public record ConsultaResponseDTO(
        Long codconsulta,
        LocalDateTime datahora,
        String motivo,
        PacienteResponseDTO paciente,
        MedicoResponseDTO medico
) {
}
