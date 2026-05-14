package com.hospital.hms.dto.response;

public record ItemReceitaResponseDTO(
        Long codreceitafk,
        Long codmedicamentofk,
        String posologia,
        ReceitaResponseDTO receita,
        MedicamentoResponseDTO medicamento
) {
}
