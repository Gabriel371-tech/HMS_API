package com.hospital.hms.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ExameConsultaId implements Serializable {

    private Long codconsulta;

    private Long codexame;

    public ExameConsultaId() {
    }

    public ExameConsultaId(Long codconsulta, Long codexame) {
        this.codconsulta = codconsulta;
        this.codexame = codexame;
    }

    public Long getCodconsulta() {
        return codconsulta;
    }

    public void setCodconsulta(Long codconsulta) {
        this.codconsulta = codconsulta;
    }

    public Long getCodexame() {
        return codexame;
    }

    public void setCodexame(Long codexame) {
        this.codexame = codexame;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExameConsultaId that)) return false;
        return Objects.equals(codconsulta, that.codconsulta) && Objects.equals(codexame, that.codexame);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codconsulta, codexame);
    }
}
