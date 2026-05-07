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
@Table(name = "alas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codala;

    private String nome;

    private Integer andar;

    public Long getCodala() {
        return codala;
    }

    public void setCodala(Long codala) {
        this.codala = codala;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAndar() {
        return andar;
    }

    public void setAndar(Integer andar) {
        this.andar = andar;
    }
}
