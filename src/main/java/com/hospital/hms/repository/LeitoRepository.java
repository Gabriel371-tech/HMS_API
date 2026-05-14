package com.hospital.hms.repository;

import com.hospital.hms.model.Leito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LeitoRepository extends JpaRepository<Leito, Long> {
    List<Leito> findByStatusIgnoreCase(String status);

    long countByQuartoCodquarto(Long codquarto);

    long countByQuartoCodquartoAndCodleitoNot(Long codquarto, Long codleito);
}
