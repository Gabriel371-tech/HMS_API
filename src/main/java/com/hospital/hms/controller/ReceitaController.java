package com.hospital.hms.controller;

import com.hospital.hms.dto.DtoMapper;
import com.hospital.hms.dto.request.ReceitaRequestDTO;
import com.hospital.hms.dto.response.ReceitaResponseDTO;
import com.hospital.hms.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @GetMapping
    public List<ReceitaResponseDTO> listar() {
        return receitaService.listarTodos().stream().map(DtoMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceitaResponseDTO> buscar(@PathVariable Long id) {
        return receitaService.buscarPorId(id)
                .map(DtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReceitaResponseDTO> salvar(@RequestBody ReceitaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DtoMapper.toResponse(receitaService.salvar(DtoMapper.toEntity(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReceitaResponseDTO> atualizar(@PathVariable Long id, @RequestBody ReceitaRequestDTO request) {
        return receitaService.buscarPorId(id)
                .map(receitaExistente -> {
                    var receita = DtoMapper.toEntity(request);
                    receita.setCodreceita(id);
                    return ResponseEntity.ok(DtoMapper.toResponse(receitaService.salvar(receita)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (receitaService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        receitaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
