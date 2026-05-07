package com.hospital.hms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quartos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codquarto;

    private Integer numero;

    private String tipo;

    @ManyToOne
    @JoinColumn(name = "codalafk")
    private Ala ala;

    public Long getCodquarto() {
        return codquarto;
    }

    public void setCodquarto(Long codquarto) {
        this.codquarto = codquarto;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Ala getAla() {
        return ala;
    }

    public void setAla(Ala ala) {
        this.ala = ala;
    }
}
