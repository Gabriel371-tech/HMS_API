package com.hospital.hms.repository;

import com.hospital.hms.model.ExameConsulta;
import com.hospital.hms.model.ExameConsultaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExameConsultaRepository extends JpaRepository<ExameConsulta, ExameConsultaId> {
}
