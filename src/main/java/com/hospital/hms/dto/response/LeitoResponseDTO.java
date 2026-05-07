package com.hospital.hms.dto.response;

public record LeitoResponseDTO(Long codleito, Integer numero, String status, QuartoResponseDTO quarto) {
}
