package com.hospital.hms.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ItemReceitaId implements Serializable {

    private Long codreceita;

    private Long codmedicamento;

    public ItemReceitaId() {
    }

    public ItemReceitaId(Long codreceita, Long codmedicamento) {
        this.codreceita = codreceita;
        this.codmedicamento = codmedicamento;
    }

    public Long getCodreceita() {
        return codreceita;
    }

    public void setCodreceita(Long codreceita) {
        this.codreceita = codreceita;
    }

    public Long getCodmedicamento() {
        return codmedicamento;
    }

    public void setCodmedicamento(Long codmedicamento) {
        this.codmedicamento = codmedicamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemReceitaId that)) return false;
        return Objects.equals(codreceita, that.codreceita)
                && Objects.equals(codmedicamento, that.codmedicamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codreceita, codmedicamento);
    }
}
