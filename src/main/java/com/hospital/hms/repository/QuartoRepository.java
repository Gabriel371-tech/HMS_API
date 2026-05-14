package com.hospital.hms.repository;

import com.hospital.hms.model.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {
    boolean existsByNumeroAndAlaCodala(Integer numero, Long codala);

    boolean existsByNumeroAndAlaCodalaAndCodquartoNot(Integer numero, Long codala, Long codquarto);
}
