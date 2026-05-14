package com.hospital.hms.controller;

import com.hospital.hms.dto.DtoMapper;
import com.hospital.hms.dto.request.MedicamentoRequestDTO;
import com.hospital.hms.dto.response.MedicamentoResponseDTO;
import com.hospital.hms.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    @GetMapping
    public List<MedicamentoResponseDTO> listar() {
        return medicamentoService.listarTodos().stream().map(DtoMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> buscar(@PathVariable Long id) {
        return medicamentoService.buscarPorId(id)
                .map(DtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MedicamentoResponseDTO> salvar(@RequestBody MedicamentoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DtoMapper.toResponse(medicamentoService.salvar(DtoMapper.toEntity(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody MedicamentoRequestDTO request
    ) {
        return medicamentoService.buscarPorId(id)
                .map(medicamentoExistente -> {
                    var medicamento = DtoMapper.toEntity(request);
                    medicamento.setCodmedicamento(id);
                    return ResponseEntity.ok(DtoMapper.toResponse(medicamentoService.salvar(medicamento)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (medicamentoService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        medicamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
