package com.hospital.hms.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "examesconsulta")
public class ExameConsulta {

    @EmbeddedId
    private ExameConsultaId id;

    @ManyToOne
    @MapsId("codconsulta")
    @JoinColumn(name = "codconsultafk")
    private Consulta consulta;

    private String resultadourl;

    private LocalDate datarealizacao;

    public ExameConsultaId getId() {
        return id;
    }

    public void setId(ExameConsultaId id) {
        this.id = id;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public String getResultadourl() {
        return resultadourl;
    }

    public void setResultadourl(String resultadourl) {
        this.resultadourl = resultadourl;
    }

    public LocalDate getDatarealizacao() {
        return datarealizacao;
    }

    public void setDatarealizacao(LocalDate datarealizacao) {
        this.datarealizacao = datarealizacao;
    }
}
