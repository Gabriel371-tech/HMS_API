package com.hospital.hms.service;

import com.hospital.hms.model.Leito;
import com.hospital.hms.repository.LeitoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LeitoService {

    public static final String STATUS_LIVRE = "livre";
    public static final String STATUS_OCUPADO = "ocupado";
    public static final String STATUS_MANUTENCAO = "manutencao";

    @Autowired
    private LeitoRepository leitoRepository;

    public List<Leito> listarTodos() {
        return leitoRepository.findAll();
    }

    public List<Leito> listarDisponiveis() {
        return leitoRepository.findByStatusIgnoreCase(STATUS_LIVRE);
    }

    public Optional<Leito> buscarPorId(Long id) {
        return leitoRepository.findById(id);
    }

    public Leito salvar(Leito leito) {
        normalizarEValidarStatus(leito);
        if (STATUS_OCUPADO.equals(leito.getStatus())) {
            throw new IllegalArgumentException("Para ocupar um leito, use a operacao especifica de ocupacao.");
        }

        return leitoRepository.save(leito);
    }

    public Optional<Leito> atualizar(Long id, Leito leitoAtualizado) {
        return leitoRepository.findById(id).map(leitoExistente -> {
            String statusAnterior = normalizarStatus(leitoExistente.getStatus());
            normalizarEValidarStatus(leitoAtualizado);

            if (!statusAnterior.equals(leitoAtualizado.getStatus())
                    && (STATUS_OCUPADO.equals(statusAnterior) || STATUS_OCUPADO.equals(leitoAtualizado.getStatus()))) {
                throw new IllegalStateException("Use as operacoes especificas para ocupar ou liberar um leito.");
            }

            leitoAtualizado.setCodleito(id);
            return leitoRepository.save(leitoAtualizado);
        });
    }

    public Optional<Leito> ocupar(Long id) {
        return leitoRepository.findById(id).map(leito -> {
            String statusAtual = normalizarStatus(leito.getStatus());

            if (!STATUS_LIVRE.equals(statusAtual)) {
                throw new IllegalStateException("Leito indisponivel para ocupacao. Status atual: " + statusAtual + ".");
            }

            leito.setStatus(STATUS_OCUPADO);
            return leitoRepository.save(leito);
        });
    }

    public Optional<Leito> liberar(Long id) {
        return leitoRepository.findById(id).map(leito -> {
            leito.setStatus(STATUS_LIVRE);
            return leitoRepository.save(leito);
        });
    }

    public boolean existePorId(Long id) {
        return leitoRepository.existsById(id);
    }

    public void excluir(Long id) {
        Leito leito = leitoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Leito nao encontrado."));

        if (STATUS_OCUPADO.equals(normalizarStatus(leito.getStatus()))) {
            throw new IllegalStateException("Nao e possivel excluir um leito ocupado.");
        }

        leitoRepository.delete(leito);
    }

    private void normalizarEValidarStatus(Leito leito) {
        if (leito == null) {
            throw new IllegalArgumentException("Leito nao informado.");
        }

        leito.setStatus(normalizarStatus(leito.getStatus()));
    }

    private String normalizarStatus(String status) {
        if (status == null || status.isBlank()) {
            return STATUS_LIVRE;
        }

        String statusNormalizado = status.trim().toLowerCase();
        if (STATUS_LIVRE.equals(statusNormalizado)
                || STATUS_OCUPADO.equals(statusNormalizado)
                || STATUS_MANUTENCAO.equals(statusNormalizado)) {
            return statusNormalizado;
        }

        throw new IllegalArgumentException("Status de leito invalido. Use livre, ocupado ou manutencao.");
    }
}
