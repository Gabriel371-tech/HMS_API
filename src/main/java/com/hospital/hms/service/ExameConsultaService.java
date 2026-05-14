package com.hospital.hms.service;

import com.hospital.hms.model.ExameConsulta;
import com.hospital.hms.model.ExameConsultaId;
import com.hospital.hms.repository.ExameConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ExameConsultaService {

    @Autowired
    private ExameConsultaRepository exameConsultaRepository;

    public List<ExameConsulta> listarTodos() {
        return exameConsultaRepository.findAll();
    }

    public Optional<ExameConsulta> buscarPorId(ExameConsultaId id) {
        return exameConsultaRepository.findById(id);
    }

    public ExameConsulta salvar(ExameConsulta exameConsulta) {
        return exameConsultaRepository.save(exameConsulta);
    }

    public void excluir(ExameConsultaId id) {
        exameConsultaRepository.deleteById(id);
    }
}
