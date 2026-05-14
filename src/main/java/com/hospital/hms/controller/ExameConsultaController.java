package com.hospital.hms.controller;

import com.hospital.hms.dto.DtoMapper;
import com.hospital.hms.dto.request.ExameConsultaRequestDTO;
import com.hospital.hms.dto.response.ExameConsultaResponseDTO;
import com.hospital.hms.model.ExameConsultaId;
import com.hospital.hms.service.ExameConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/exames-consulta")
public class ExameConsultaController {

    @Autowired
    private ExameConsultaService exameConsultaService;

    @GetMapping
    public List<ExameConsultaResponseDTO> listar() {
        return exameConsultaService.listarTodos().stream().map(DtoMapper::toResponse).toList();
    }

    @GetMapping("/{codconsulta}/{codexame}")
    public ResponseEntity<ExameConsultaResponseDTO> buscar(@PathVariable Long codconsulta, @PathVariable Long codexame) {
        return exameConsultaService.buscarPorId(new ExameConsultaId(codconsulta, codexame))
                .map(DtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ExameConsultaResponseDTO> salvar(@RequestBody ExameConsultaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DtoMapper.toResponse(exameConsultaService.salvar(DtoMapper.toEntity(request))));
    }

    @PutMapping("/{codconsulta}/{codexame}")
    public ResponseEntity<ExameConsultaResponseDTO> atualizar(
            @PathVariable Long codconsulta,
            @PathVariable Long codexame,
            @RequestBody ExameConsultaRequestDTO request
    ) {
        ExameConsultaId id = new ExameConsultaId(codconsulta, codexame);
        return exameConsultaService.buscarPorId(id)
                .map(exameExistente -> {
                    var exameConsulta = DtoMapper.toEntity(new ExameConsultaRequestDTO(
                            codconsulta,
                            codexame,
                            request.resultadourl(),
                            request.datarealizacao()
                    ));
                    return ResponseEntity.ok(DtoMapper.toResponse(exameConsultaService.salvar(exameConsulta)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{codconsulta}/{codexame}")
    public ResponseEntity<Void> excluir(@PathVariable Long codconsulta, @PathVariable Long codexame) {
        ExameConsultaId id = new ExameConsultaId(codconsulta, codexame);
        if (exameConsultaService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        exameConsultaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
