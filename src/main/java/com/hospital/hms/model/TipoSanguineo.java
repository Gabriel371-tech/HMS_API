package com.hospital.hms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipossanguineos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoSanguineo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codtipo;

    private String tipo;

    private String fatorrh;

    public Long getCodtipo() {
        return codtipo;
    }

    public void setCodtipo(Long codtipo) {
        this.codtipo = codtipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFatorrh() {
        return fatorrh;
    }

    public void setFatorrh(String fatorrh) {
        this.fatorrh = fatorrh;
    }
}
