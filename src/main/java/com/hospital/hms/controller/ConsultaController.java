package com.hospital.hms.controller;

import com.hospital.hms.dto.DtoMapper;
import com.hospital.hms.dto.request.ConsultaRequestDTO;
import com.hospital.hms.dto.response.ConsultaResponseDTO;
import com.hospital.hms.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping
    public List<ConsultaResponseDTO> listar() {
        return consultaService.listarTodos().stream().map(DtoMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> buscar(@PathVariable Long id) {
        return consultaService.buscarPorId(id)
                .map(DtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> salvar(@RequestBody ConsultaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DtoMapper.toResponse(consultaService.salvar(DtoMapper.toEntity(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> atualizar(@PathVariable Long id, @RequestBody ConsultaRequestDTO request) {
        return consultaService.buscarPorId(id)
                .map(consultaExistente -> {
                    var consulta = DtoMapper.toEntity(request);
                    consulta.setCodconsulta(id);
                    return ResponseEntity.ok(DtoMapper.toResponse(consultaService.salvar(consulta)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (consultaService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        consultaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
