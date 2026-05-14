package com.hospital.hms.dto.request;

import java.time.LocalDateTime;

public record ConsultaRequestDTO(
        LocalDateTime datahora,
        String motivo,
        Long codpacientefk,
        Long codmedicofk
) {
}
