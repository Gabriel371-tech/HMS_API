package com.hospital.hms.dto.response;

import java.time.LocalDate;

public record ExameConsultaResponseDTO(
        Long codconsultafk,
        Long codexamefk,
        String resultadourl,
        LocalDate datarealizacao,
        ConsultaResponseDTO consulta
) {
}
