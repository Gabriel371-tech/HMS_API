package com.hospital.hms.dto.request;

import java.time.LocalDate;

public record ExameConsultaRequestDTO(
        Long codconsultafk,
        Long codexamefk,
        String resultadourl,
        LocalDate datarealizacao
) {
}
