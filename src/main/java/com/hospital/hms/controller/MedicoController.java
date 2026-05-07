package com.hospital.hms.controller;

import com.hospital.hms.dto.DtoMapper;
import com.hospital.hms.dto.request.MedicoRequestDTO;
import com.hospital.hms.dto.response.MedicoResponseDTO;
import com.hospital.hms.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    public List<MedicoResponseDTO> listar() {
        return medicoService.listarTodos().stream()
                .map(DtoMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> buscar(@PathVariable Long id) {
        return medicoService.buscarPorId(id)
                .map(DtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MedicoResponseDTO> salvar(@RequestBody MedicoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DtoMapper.toResponse(medicoService.salvar(DtoMapper.toEntity(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> atualizar(@PathVariable Long id, @RequestBody MedicoRequestDTO request) {
        return medicoService.buscarPorId(id)
                .map(medicoExistente -> {
                    var medico = DtoMapper.toEntity(request);
                    medico.setCodmedico(id);
                    return ResponseEntity.ok(DtoMapper.toResponse(medicoService.salvar(medico)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (medicoService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        medicoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
