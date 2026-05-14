package com.hospital.hms.dto.response;

import java.time.LocalDate;

public record ReceitaResponseDTO(Long codreceita, LocalDate validade, ConsultaResponseDTO consulta) {
}
