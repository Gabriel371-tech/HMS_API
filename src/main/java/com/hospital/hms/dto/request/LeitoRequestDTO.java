package com.hospital.hms.dto.request;

public record LeitoRequestDTO(Integer numero, String status, Long codquarto, QuartoReferenceDTO quarto) {
}
