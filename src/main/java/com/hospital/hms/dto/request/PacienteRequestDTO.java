package com.hospital.hms.dto.request;

import java.time.LocalDate;

public record PacienteRequestDTO(
        String nome,
        LocalDate datanasc,
        LocalDate dataNascimento,
        Long codtipodk,
        Long codtipo,
        TipoSanguineoReferenceDTO tipoSanguineo
) {
}
