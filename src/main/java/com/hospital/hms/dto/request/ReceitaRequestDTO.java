package com.hospital.hms.dto.request;

import java.time.LocalDate;

public record ReceitaRequestDTO(LocalDate validade, Long codconsultafk) {
}
