package com.hospital.hms.controller;

import com.hospital.hms.dto.DtoMapper;
import com.hospital.hms.dto.request.AlaRequestDTO;
import com.hospital.hms.dto.response.AlaResponseDTO;
import com.hospital.hms.service.AlaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alas")
public class AlaController {

    @Autowired
    private AlaService alaService;

    @GetMapping
    public List<AlaResponseDTO> listar() {
        return alaService.listarTodos().stream()
                .map(DtoMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlaResponseDTO> buscar(@PathVariable Long id) {
        return alaService.buscarPorId(id)
                .map(DtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AlaResponseDTO> salvar(@RequestBody AlaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DtoMapper.toResponse(alaService.salvar(DtoMapper.toEntity(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlaResponseDTO> atualizar(@PathVariable Long id, @RequestBody AlaRequestDTO request) {
        return alaService.buscarPorId(id)
                .map(alaExistente -> {
                    var ala = DtoMapper.toEntity(request);
                    ala.setCodala(id);
                    return ResponseEntity.ok(DtoMapper.toResponse(alaService.salvar(ala)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (alaService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        alaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
