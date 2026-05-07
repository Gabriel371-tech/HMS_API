package com.hospital.hms.controller;

import com.hospital.hms.dto.DtoMapper;
import com.hospital.hms.dto.request.QuartoRequestDTO;
import com.hospital.hms.dto.response.QuartoResponseDTO;
import com.hospital.hms.service.QuartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/quartos")
public class QuartoController {

    @Autowired
    private QuartoService quartoService;

    @GetMapping
    public List<QuartoResponseDTO> listar() {
        return quartoService.listarTodos().stream()
                .map(DtoMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuartoResponseDTO> buscar(@PathVariable Long id) {
        return quartoService.buscarPorId(id)
                .map(DtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<QuartoResponseDTO> salvar(@RequestBody QuartoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DtoMapper.toResponse(quartoService.salvar(DtoMapper.toEntity(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuartoResponseDTO> atualizar(@PathVariable Long id, @RequestBody QuartoRequestDTO request) {
        return quartoService.buscarPorId(id)
                .map(quartoExistente -> {
                    var quarto = DtoMapper.toEntity(request);
                    quarto.setCodquarto(id);
                    return ResponseEntity.ok(DtoMapper.toResponse(quartoService.salvar(quarto)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (quartoService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        quartoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
