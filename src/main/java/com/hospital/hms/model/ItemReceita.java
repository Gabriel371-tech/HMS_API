package com.hospital.hms.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "itensreceita")
public class ItemReceita {

    @EmbeddedId
    private ItemReceitaId id;

    @ManyToOne
    @MapsId("codreceita")
    @JoinColumn(name = "codreceitafk")
    private Receita receita;

    @ManyToOne
    @MapsId("codmedicamento")
    @JoinColumn(name = "codmedicamentofk")
    private Medicamento medicamento;

    private String posologia;

    public ItemReceitaId getId() {
        return id;
    }

    public void setId(ItemReceitaId id) {
        this.id = id;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public String getPosologia() {
        return posologia;
    }

    public void setPosologia(String posologia) {
        this.posologia = posologia;
    }
}
