package com.hospital.hms.controller;

import com.hospital.hms.dto.DtoMapper;
import com.hospital.hms.dto.request.PacienteRequestDTO;
import com.hospital.hms.dto.response.PacienteResponseDTO;
import com.hospital.hms.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public List<PacienteResponseDTO> listar() {
        return pacienteService.listarTodos().stream()
                .map(DtoMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> buscar(@PathVariable Long id) {
        return pacienteService.buscarPorId(id)
                .map(DtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PacienteResponseDTO> salvar(@RequestBody PacienteRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DtoMapper.toResponse(pacienteService.salvar(DtoMapper.toEntity(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody PacienteRequestDTO request
    ) {
        return pacienteService.buscarPorId(id)
                .map(pacienteExistente -> {
                    var paciente = DtoMapper.toEntity(request);
                    paciente.setCodpaciente(id);
                    return ResponseEntity.ok(DtoMapper.toResponse(pacienteService.salvar(paciente)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (pacienteService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        pacienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
