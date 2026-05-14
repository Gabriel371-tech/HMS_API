package com.hospital.hms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "medicamentos")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codmedicamento;

    private String nomegenerico;

    private String laboratorio;

    public Long getCodmedicamento() {
        return codmedicamento;
    }

    public void setCodmedicamento(Long codmedicamento) {
        this.codmedicamento = codmedicamento;
    }

    public String getNomegenerico() {
        return nomegenerico;
    }

    public void setNomegenerico(String nomegenerico) {
        this.nomegenerico = nomegenerico;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }
}
