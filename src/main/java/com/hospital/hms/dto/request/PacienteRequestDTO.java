package com.hospital.hms.dto.request;

import java.time.LocalDate;

public record PacienteRequestDTO(
        String nome,
        String cpf,
        LocalDate dataNascimento,
        Long codtipo,
        TipoSanguineoReferenceDTO tipoSanguineo
) {
}
