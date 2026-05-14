package com.hospital.hms.repository;

import com.hospital.hms.model.ItemReceita;
import com.hospital.hms.model.ItemReceitaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemReceitaRepository extends JpaRepository<ItemReceita, ItemReceitaId> {
}
