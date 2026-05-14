package com.hospital.hms.service;

import com.hospital.hms.model.Quarto;
import com.hospital.hms.repository.LeitoRepository;
import com.hospital.hms.repository.QuartoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class QuartoService {

    public static final String TIPO_PRIVATIVO = "privativo";
    public static final String TIPO_SEMIPRIVATIVO = "semiprivativo";
    public static final String TIPO_ENFERMARIA = "enfermaria";

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private LeitoRepository leitoRepository;

    public List<Quarto> listarTodos() {
        return quartoRepository.findAll();
    }

    public Optional<Quarto> buscarPorId(Long id) {
        return quartoRepository.findById(id);
    }

    public Quarto salvar(Quarto quarto) {
        normalizarEValidar(quarto);
        return quartoRepository.save(quarto);
    }

    public Optional<Quarto> atualizar(Long id, Quarto quartoAtualizado) {
        return quartoRepository.findById(id).map(quartoExistente -> {
            quartoAtualizado.setCodquarto(id);
            normalizarEValidar(quartoAtualizado);
            return quartoRepository.save(quartoAtualizado);
        });
    }

    public void excluir(Long id) {
        quartoRepository.deleteById(id);
    }

    public int capacidadePorTipo(String tipo) {
        String tipoNormalizado = normalizarTipo(tipo);
        return switch (tipoNormalizado) {
            case TIPO_PRIVATIVO -> 1;
            case TIPO_SEMIPRIVATIVO -> 2;
            case TIPO_ENFERMARIA -> 6;
            default -> throw new IllegalArgumentException("Tipo de quarto invalido.");
        };
    }

    private void normalizarEValidar(Quarto quarto) {
        if (quarto == null) {
            throw new IllegalArgumentException("Quarto nao informado.");
        }

        quarto.setTipo(normalizarTipo(quarto.getTipo()));

        if (quarto.getNumero() == null) {
            throw new IllegalArgumentException("Numero do quarto e obrigatorio.");
        }

        if (quarto.getAla() == null || quarto.getAla().getCodala() == null) {
            throw new IllegalArgumentException("Ala do quarto e obrigatoria.");
        }

        boolean numeroJaUsado = quarto.getCodquarto() == null
                ? quartoRepository.existsByNumeroAndAlaCodala(quarto.getNumero(), quarto.getAla().getCodala())
                : quartoRepository.existsByNumeroAndAlaCodalaAndCodquartoNot(
                        quarto.getNumero(),
                        quarto.getAla().getCodala(),
                        quarto.getCodquarto()
                );

        if (numeroJaUsado) {
            throw new IllegalStateException("Ja existe um quarto com este numero na ala informada.");
        }

        if (quarto.getCodquarto() != null) {
            long totalLeitos = leitoRepository.countByQuartoCodquarto(quarto.getCodquarto());
            int capacidade = capacidadePorTipo(quarto.getTipo());
            if (totalLeitos > capacidade) {
                throw new IllegalStateException("O quarto ja possui mais leitos do que o tipo informado comporta.");
            }
        }
    }

    private String normalizarTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("Tipo do quarto e obrigatorio.");
        }

        String tipoNormalizado = tipo.trim().toLowerCase();
        if (TIPO_PRIVATIVO.equals(tipoNormalizado)
                || TIPO_SEMIPRIVATIVO.equals(tipoNormalizado)
                || TIPO_ENFERMARIA.equals(tipoNormalizado)) {
            return tipoNormalizado;
        }

        throw new IllegalArgumentException("Tipo de quarto invalido. Use privativo, semiprivativo ou enfermaria.");
    }
}
