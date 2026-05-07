package com.hospital.hms.controller;

import com.hospital.hms.dto.DtoMapper;
import com.hospital.hms.dto.request.LeitoRequestDTO;
import com.hospital.hms.dto.response.LeitoResponseDTO;
import com.hospital.hms.service.LeitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leitos")
public class LeitoController {

    @Autowired
    private LeitoService leitoService;

    @GetMapping
    public List<LeitoResponseDTO> listar() {
        return leitoService.listarTodos().stream()
                .map(DtoMapper::toResponse)
                .toList();
    }

    @GetMapping("/disponiveis")
    public List<LeitoResponseDTO> listarDisponiveis() {
        return leitoService.listarDisponiveis().stream()
                .map(DtoMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeitoResponseDTO> buscar(@PathVariable Long id) {
        return leitoService.buscarPorId(id)
                .map(DtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody LeitoRequestDTO request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(DtoMapper.toResponse(leitoService.salvar(DtoMapper.toEntity(request))));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("erro", ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody LeitoRequestDTO request) {
        try {
            return leitoService.atualizar(id, DtoMapper.toEntity(request))
                    .map(DtoMapper::toResponse)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("erro", ex.getMessage()));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", ex.getMessage()));
        }
    }

    @PatchMapping("/{id}/ocupar")
    public ResponseEntity<?> ocupar(@PathVariable Long id) {
        try {
            return leitoService.ocupar(id)
                    .map(DtoMapper::toResponse)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", ex.getMessage()));
        }
    }

    @PatchMapping("/{id}/liberar")
    public ResponseEntity<?> liberar(@PathVariable Long id) {
        return leitoService.liberar(id)
                .map(DtoMapper::toResponse)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        try {
            leitoService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", ex.getMessage()));
        }
    }
}
